package com.xslite.sharemyaccountnative.data.config

import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.data.models.AccountSectionModel
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.util.AccountType


/**
 * Temporary config from collected accounts,
 * will soon move to json file, and then to rest server
 */
class AccountConfig {


    /**
     * List of all accounts that can be created
     */
    private val getAllAccounts = mutableListOf(
        AccountModel(
            title = "Vkontakte",
            image = R.drawable.social_vk,
            type = AccountType.SOCIAL,
            baseUrl = "https://vk.com/"
        ),
        AccountModel(
            title = "Telegram",
            image = R.drawable.social_telegram,
            type = AccountType.SOCIAL,
            baseUrl = "https://t.me/"
        ),
        AccountModel(
            title = "Github",
            image = R.drawable.social_github,
            type = AccountType.SOCIAL,
            baseUrl = "https://github.com/"
        ),
        AccountModel(
            title = "Steam",
            image = R.drawable.social_steam,
            type = AccountType.SOCIAL,
            baseUrl = "https://steamcommunity.com/id/"
        ),
        AccountModel(
            title = "Facebook",
            image = R.drawable.social_facebook,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.facebook.com/"
        ),
        AccountModel(
            title = "Instagram",
            image = R.drawable.social_inst,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.instagram.com/"
        ),
        AccountModel(
            title = "Pinterest",
            image = R.drawable.social_pinterest,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.pinterest.ru/"
        ),
        AccountModel(
            title = "Likee",
            image = R.drawable.social_likee,
            type = AccountType.SOCIAL,
            baseUrl = "https://likee.video/@"
        ),
        AccountModel(
            title = "TikTok",
            image = R.drawable.social_tiktok,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.tiktok.com/@"
        ),
        AccountModel(
            title = "Tinder",
            image = R.drawable.social_tinder,
            type = AccountType.SOCIAL,
            baseUrl = "https://tinder.com/@"
        ),
        AccountModel(
            title = "Twich",
            image = R.drawable.social_twich,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.twitch.tv/"

        ),
        AccountModel(
            title = "Twitter",
            image = R.drawable.social_twitter,
            type = AccountType.SOCIAL,
            baseUrl = "https://twitter.com/"
        ),
        AccountModel(
            title = "YouTube",
            image = R.drawable.social_youtube,
            type = AccountType.SOCIAL,
            baseUrl = "https://www.youtube.com/channel/"
        ),
    )

    /**
     * List of ready-made sections for accounts
     */
    val getNewAccountList = listOf(
        AccountSectionModel(
            id = AccountType.EMAIL,
            title = AccountType.EMAIL.title,
            items = mutableListOf(
                AccountModel(
                    id = 0,
                    title = "email",
                    type = AccountType.EMAIL,
                    image = R.drawable.social_email
                )
            )
        ),
        AccountSectionModel(
            id = AccountType.CUSTOM,
            title = AccountType.CUSTOM.title,
            items = mutableListOf(
                AccountModel(
                    id = 0,
                    title = "website",
                    type = AccountType.CUSTOM,
                    image = R.drawable.social_custom
                )
            )
        ),
        AccountSectionModel(
            id = AccountType.SOCIAL,
            title = AccountType.SOCIAL.title,
            items = getAllAccounts
        )
    )
}