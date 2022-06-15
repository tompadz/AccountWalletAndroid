package com.xslite.sharemyaccountnative.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.SheetAccountActionsBinding
import com.xslite.sharemyaccountnative.util.AccountType
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.copyToClipboard
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.openLink
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.shareLink
import com.xslite.sharemyaccountnative.util.listeners.OnAccountActionSheetListener

class SheetAccountActions(
    private val account:AccountModel,
    private val listener:OnAccountActionSheetListener
) : BottomSheetDialogFragment() {

    private var _binding:SheetAccountActionsBinding ? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SheetAccountActions"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetAccountActionsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            if (account.type == AccountType.EMAIL) {
                buttonOpenLink.isVisible = false
                buttonCopy.setText(R.string.button_copy_email)
                buttonShare.setText(R.string.button_share_email)
                buttonRemove.setText(R.string.button_delete_email)
            }
        }

        binding.apply {
            val buttonListener = View.OnClickListener {
                when (it.id) {
                    buttonRemove.id -> {
                        listener.onDeleteClick()
                    }
                    buttonShare.id -> {
                        requireContext().shareLink(link = account.url)
                        listener.onShareClick()
                    }
                    buttonCopy.id -> {
                        requireContext().copyToClipboard(account.url)
                        listener.onCopyClick()
                    }
                    buttonOpenLink.id -> {
                        requireContext().openLink(link = account.url)
                        listener.openInBrowser()
                    }
                }
                this@SheetAccountActions.dismiss()
            }
            buttonRemove.setOnClickListener(buttonListener)
            buttonShare.setOnClickListener(buttonListener)
            buttonCopy.setOnClickListener(buttonListener)
            buttonOpenLink.setOnClickListener(buttonListener)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}