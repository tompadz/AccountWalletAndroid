package com.xslite.sharemyaccountnative.di

import android.content.Context
import androidx.room.Room
import com.xslite.sharemyaccountnative.data.db.AccountsDataBase
import com.xslite.sharemyaccountnative.util.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAccountsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AccountsDataBase::class.java,
        "accounts_database"
    ).build()


    @Singleton
    @Provides
    fun provideAccountsDao(db:AccountsDataBase) = db.accountsDao()

    @Singleton
    @Provides
    fun providePrefs(
        @ApplicationContext app: Context
    ) = SharedPrefs(context = app)
}