package com.xslite.sharemyaccountnative.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.FragmentCreateAccountBinding
import com.xslite.sharemyaccountnative.ui.view_models.CreateAccountViewModel
import com.xslite.sharemyaccountnative.util.AccountType.*
import com.xslite.sharemyaccountnative.util.AppTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private var _binding : FragmentCreateAccountBinding? = null
    private val binding get() = _binding !!
    private val args : CreateAccountFragmentArgs by navArgs()
    private val viewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * material motion
         * https://material.io/develop/android/theming/motion
         */
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        //get account from navigation args
        val account =
            Gson().fromJson<AccountModel>(args.account, object : TypeToken<AccountModel>() {}.type)

        binding.apply {
            toolbar.title = account.title
            textLink.text = account.baseUrl
            //The text for links and mail is different, so we check the account type and change the text on the page
            when (account.type){
                EMAIL -> {
                    textDesc.text = getString(R.string.text_create_desc_email)
                    textField.hint = getString(R.string.hint_create_email)
                }
                SOCIAL -> {
                    textDesc.text = getString(R.string.text_create_desc, account.title)
                    textField.hint = getString(R.string.hint_create_account, account.title)
                    textLink.isVisible = true
                    textLinkDescr.isVisible = true
                }
                CUSTOM -> {
                    textDesc.text = getString(R.string.text_create_desc_custom_web)
                    textField.hint = getString(R.string.hint_create_custom_web)
                }
            }
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        binding.apply {
            textField.addTextChangedListener(object : AppTextWatcher() {
                @SuppressLint("SetTextI18n")
                override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                    textLink.text = account.baseUrl + p0
                }
            })
        }

        binding.apply {
            button.setOnClickListener{
                viewModel.addNewAccount(account, textField.text.trim().toString())
            }
        }

        lifecycleScope.launch  {
            viewModel.uiState
                .map { it.onAccountCreate }
                .distinctUntilChanged()
                .collect {
                    if (it){
                        requireActivity().onBackPressed()
                    }
                }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}