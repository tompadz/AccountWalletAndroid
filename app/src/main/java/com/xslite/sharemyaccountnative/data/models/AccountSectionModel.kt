package com.xslite.sharemyaccountnative.data.models

import androidx.annotation.StringRes
import com.xslite.sharemyaccountnative.util.AccountType

/**
 * @param title Section title
 * @param items List of accounts that are included in the section
 */
data class AccountSectionModel(
    val id:AccountType,
    @StringRes val title:Int,
    val items:MutableList<AccountModel> = mutableListOf()
)
