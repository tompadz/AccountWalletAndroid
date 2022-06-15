package com.xslite.sharemyaccountnative.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.data.repository.AccountRepository
import com.xslite.sharemyaccountnative.data.state.CreateAccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val repository : AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState : StateFlow<CreateAccountUiState> = _uiState.asStateFlow()

    fun addNewAccount(account : AccountModel, id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            account.accountId = id
            account.url = account.baseUrl + account.accountId
            repository.addAccount(account)
            _uiState.update {
                it.copy(onAccountCreate = true)
            }
        }
    }
}