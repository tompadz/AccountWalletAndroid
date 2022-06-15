package com.xslite.sharemyaccountnative.ui.view_models

import androidx.lifecycle.ViewModel
import com.xslite.sharemyaccountnative.data.repository.AccountRepository
import com.xslite.sharemyaccountnative.data.state.UserSectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository : AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserSectionUiState())
    val uiState : StateFlow<UserSectionUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(sections = repository.getAllSocial())
        }
    }
}