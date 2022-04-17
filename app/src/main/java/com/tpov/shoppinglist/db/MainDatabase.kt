package com.tpov.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpov.shoppinglist.entities.LibraryItem
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [LibraryItem::class, NoteItem::class,
    ShopingListItem::class, ShopingListName::class, CrimeNewQuiz::class], version = 1, exportSchema = true)
abstract class MainDatabase: RoomDatabase() {
    abstract fun getDao() : Dao
    companion object {
        @Volatile
        var INSTANCE: MainDatabase? = null

        @InternalCoroutinesApi
        fun getDataBase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "shoping_list.db"
                ).build()
                instance
            }
        }
    }
}