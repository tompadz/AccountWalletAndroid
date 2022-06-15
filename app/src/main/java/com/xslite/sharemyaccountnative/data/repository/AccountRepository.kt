package com.xslite.sharemyaccountnative.data.repository

import androidx.lifecycle.LiveData
import com.xslite.sharemyaccountnative.data.config.AccountConfig
import com.xslite.sharemyaccountnative.data.dao.AccountsDao
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.data.models.AccountSectionModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AccountRepository @Inject constructor(
    private val accountsDao : AccountsDao
) {

    /**
     * Get a list of accounts from the config
     */
    fun getAllSocial():List<AccountSectionModel> = AccountConfig().getNewAccountList

    /**
     * Get search sorted accounts from local database
     */
    fun readSearchData(query:String):LiveData<List<AccountModel>> = accountsDao.readSearchData(query)

    /**
     * Get all accounts from local database
     */
    val readAllData: LiveData<List<AccountModel>> = accountsDao.readAllData()


    suspend fun addAccount(account:AccountModel){
        accountsDao.addAccount(account)
    }

    suspend fun deleteAccount(account : AccountModel){
        accountsDao.removeAccount(account)
    }
}