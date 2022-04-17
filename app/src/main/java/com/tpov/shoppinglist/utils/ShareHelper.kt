package com.tpov.shoppinglist.utils

import android.content.Intent
import com.tpov.shoppinglist.entities.ShopingListItem

object ShareHelper {
    fun shareShopList(shopList: List<ShopingListItem>, listName: String): Intent {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, listName))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopingListItem>, listName: String): String {
        val sBuilder = StringBuilder()
        sBuilder.append("<<$listName>>")
        sBuilder.append("\n")
        var i = 0
        var itemInfo: String? = null
        shopList.forEach {
            if (it.itemInfo == null) itemInfo = " "
            sBuilder.append("${++i} - ${it.name} (${itemInfo})")
            sBuilder.append("\n")
        }
        return sBuilder.toString()
    }
}