package com.xslite.sharemyaccountnative.data.state

import com.xslite.sharemyaccountnative.data.models.AccountSectionModel

data class UserSectionUiState(
    val sections:List<AccountSectionModel> = listOf(),
)
