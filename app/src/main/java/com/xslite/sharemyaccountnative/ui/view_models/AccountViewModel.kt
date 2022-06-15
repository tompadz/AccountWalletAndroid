package com.xslite.sharemyaccountnative.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.data.models.AccountSectionModel
import com.xslite.sharemyaccountnative.data.repository.AccountRepository
import com.xslite.sharemyaccountnative.util.AccountType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository : AccountRepository
) : ViewModel() {

    val accounts = repository.readAllData

    fun deleteAccount(accountModel : AccountModel){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAccount(accountModel)
        }
    }

    fun sortAccountsToSection(accounts : List<AccountModel>) : List<AccountSectionModel> {
        //will later move to rest api
        val tempList = mutableListOf<AccountSectionModel>()
        val listSocial = mutableListOf<AccountModel>()
        val listEmail = mutableListOf<AccountModel>()
        val listCustom = mutableListOf<AccountModel>()
        accounts.sortedBy { it.type }
        accounts.map {  account ->
            when (account.type) {
                AccountType.EMAIL -> listEmail.add(account)
                AccountType.SOCIAL -> listSocial.add(account)
                AccountType.CUSTOM -> listCustom.add(account)
            }
        }
        if (listEmail.isNotEmpty()) {
            tempList.add(
                AccountSectionModel(
                    id = AccountType.EMAIL,
                    title = AccountType.EMAIL.title,
                    items = listEmail
                )
            )
        }
        if (listSocial.isNotEmpty()) {
            tempList.add(
                AccountSectionModel(
                    id = AccountType.SOCIAL,
                    title = AccountType.SOCIAL.title,
                    items = listSocial
                )
            )
        }
        if (listCustom.isNotEmpty()) {
            tempList.add(
                AccountSectionModel(
                    id = AccountType.CUSTOM,
                    title = AccountType.CUSTOM.title,
                    items = listCustom
                )
            )
        }
        return tempList
    }

}