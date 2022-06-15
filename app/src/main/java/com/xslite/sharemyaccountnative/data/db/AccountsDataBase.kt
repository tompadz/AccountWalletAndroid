package com.xslite.sharemyaccountnative.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xslite.sharemyaccountnative.data.dao.AccountsDao
import com.xslite.sharemyaccountnative.data.models.AccountModel

@Database(entities = [AccountModel::class], version = 1, exportSchema = false)
abstract class AccountsDataBase:RoomDatabase() {
    abstract fun accountsDao():AccountsDao
}