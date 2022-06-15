package com.xslite.sharemyaccountnative.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountPageViewModel @Inject constructor(
    private val repository : AccountRepository
) : ViewModel() {

    fun removeAccount(account:AccountModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(account)
        }
    }
}