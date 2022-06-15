package com.xslite.sharemyaccountnative.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xslite.sharemyaccountnative.data.models.AccountModel

/**
 * Local database of accounts,
 * will only be used for caching in the future,
 * all accounts will move to rest api
 */
@Dao
interface AccountsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccount(account:AccountModel)

    @Delete
    suspend fun removeAccount(account : AccountModel)

    /**
     * Get all accounts from local database
     */
    @Query("SELECT * FROM accounts_table ORDER BY id ASC")
    fun readAllData():LiveData<List<AccountModel>>


    /**
     * Get search sorted accounts from local database
     */
    @Query("SELECT * FROM accounts_table WHERE title LIKE :query OR accountId LIKE :query")
    fun readSearchData(query:String):LiveData<List<AccountModel>>

}