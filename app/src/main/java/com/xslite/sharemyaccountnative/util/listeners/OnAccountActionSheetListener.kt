package com.xslite.sharemyaccountnative.util.listeners

import com.xslite.sharemyaccountnative.data.models.AccountModel

interface OnAccountActionSheetListener {
    fun onDeleteClick()
    fun onShareClick() {}
    fun onCopyClick() {}
    fun openInBrowser() {}
}