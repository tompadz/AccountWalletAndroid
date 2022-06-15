package com.xslite.sharemyaccountnative.util

import androidx.annotation.StringRes
import com.xslite.sharemyaccountnative.R

enum class AccountType (@StringRes val title:Int) {
    EMAIL(R.string.account_type_email),
    SOCIAL(R.string.account_type_soc),
    CUSTOM(R.string.account_type_any),
}