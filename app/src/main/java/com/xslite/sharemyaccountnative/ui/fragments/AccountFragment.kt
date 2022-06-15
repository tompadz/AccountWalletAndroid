package com.xslite.sharemyaccountnative.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.FragmentAccountBinding
import com.xslite.sharemyaccountnative.ui.view_models.AccountPageViewModel
import com.xslite.sharemyaccountnative.util.AccountType
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.copyToClipboard
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.openLink
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.shareLink
import dagger.hilt.android.AndroidEntryPoint


/**
 * Account information page
 * On this page, the user sees a link to the entire account,
 * action menu and QR code that can be scanned
 */

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding:FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AccountPageViewModel by viewModels()
    private val args : AccountFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * material motion
         * https://material.io/develop/android/theming/motion
         */
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y,true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y,false)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {

        _binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //get account from navigation args
        val account = Gson().fromJson<AccountModel>(args.account, object : TypeToken<AccountModel>() {}.type)

        binding.apply {
            //animation of the disappearance of the qr code when scrolling the toolbar
            appbar.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
                    val offsetAlpha = appBarLayout.y / appbar.totalScrollRange
                    val value = (1 - offsetAlpha * - 1)
                    imageQr.alpha = value
                }
            )
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            toolbar.title = account.title
            //hide toolbar title
            collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        }

        binding.apply {
            //The text for links and mail is different, so we check the account type and change the text on the page
            if (account.type == AccountType.EMAIL) {
                buttonShare.text = getString(R.string.button_share_email)
                buttonOpen.isVisible = false
                buttonRemove.text = getString(R.string.button_delete_email)
                textLinkDesc.text = getString(R.string.text_link_to_email)
                textDesc.text = getString(R.string.text_qr_desc_email)
            }
        }

        //Removing account information from navigation args
        binding.apply {
            //Using QrCodeImage.kt generate qr code from account link
            imageQr.generateQrCode(account.url)
            textLink.text = account.url
            textAccountTitle.text = account.title
            imageAccount.setImageResource(account.image)
            buttonShare.setOnClickListener {
                requireContext().shareLink(account.url)
            }
            buttonOpen.setOnClickListener {
                requireContext().openLink(account.url)
            }
            buttonRemove.setOnClickListener {
                showDeleteAlert(account)
            }
            cardLink.setOnClickListener {
                requireContext().copyToClipboard(account.url)
                Snackbar.make(binding.root, getString(R.string.snackbar_link_copy),Snackbar.LENGTH_SHORT).show()
            }
        }

        return view
    }

    //Message when deleting account
    private fun showDeleteAlert(account:AccountModel){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.alert_delete_title)
            .setMessage(R.string.alert_delete_desc)
            .setPositiveButton(R.string.button_yes)
            { dialog, _ ->
                viewModel.removeAccount(account)
                dialog.cancel()
                requireActivity().onBackPressed()
            }
            .setNegativeButton(R.string.button_no)
            { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}