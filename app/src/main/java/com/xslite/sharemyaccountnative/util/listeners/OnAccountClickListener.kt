package com.xslite.sharemyaccountnative.util.listeners

import com.xslite.sharemyaccountnative.data.models.AccountModel

interface OnAccountClickListener {
    fun onAccountClick(account:AccountModel)
    fun onAccountLongClick(account : AccountModel) {}
}