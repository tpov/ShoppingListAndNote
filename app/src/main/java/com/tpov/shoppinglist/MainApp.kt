package com.tpov.shoppinglist

import android.app.Application
import com.tpov.shoppinglist.db.MainDatabase
import kotlinx.coroutines.InternalCoroutinesApi


class MainApp : Application() {
    @InternalCoroutinesApi
    val database by lazy {
        MainDatabase.getDataBase(this)
    }
}