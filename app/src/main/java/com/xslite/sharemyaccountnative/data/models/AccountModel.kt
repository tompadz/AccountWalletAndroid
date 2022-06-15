package com.xslite.sharemyaccountnative.data.models
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xslite.sharemyaccountnative.util.AccountType


/**
 * @param title account name,
 * @param image account logo
 * @param type account type from AccountType.kt
 * @param accountId user ID from the site
 * @param baseUrl default site url
 * @param url full site url and user id
 * @see AccountType
 */

@Entity(tableName = "accounts_table")
data class AccountModel(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title:String,
    @DrawableRes val image:Int,
    val type:AccountType,
    var accountId:String? = null,
    val baseUrl:String? = "",
    var url:String = "",
)
