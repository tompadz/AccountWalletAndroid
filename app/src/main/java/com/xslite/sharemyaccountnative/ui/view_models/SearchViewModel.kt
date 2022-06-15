package com.xslite.sharemyaccountnative.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository : AccountRepository
):ViewModel() {

    val accounts = repository.readAllData
    fun getAccountQuery(query:String):LiveData<List<AccountModel>> = repository.readSearchData(query)

}