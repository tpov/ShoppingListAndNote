package com.tpov.shoppinglist.db

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tpov.shoppinglist.entities.LibraryItem
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertNode(note: NoteItem)
    @Insert
    suspend fun insertShopListName(name: ShopingListName)
    @Insert
    suspend fun insertItem(note: ShopingListItem)
    @Insert
    suspend fun insertItemLibrary(libraryItem: LibraryItem)
    @Insert
    suspend fun insertCrimeNewQuiz(name: CrimeNewQuiz)

    @Query("SELECT * FROM noteList")
    fun getAllNotes() : Flow<List<NoteItem>>
    @Query("SELECT * FROM shoping_list_names")
    fun getAllShopListNames() : Flow<List<ShopingListName>>
    @Query("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItem(listId: Int) : Flow<List<ShopingListItem>>
    @Query("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name: String) :List<LibraryItem>
    @Query("SELECT * FROM new_user_table")
    suspend fun getAllIdQuestion() : List<CrimeNewQuiz>

    @Query("DELETE FROM noteList WHERE id IS :id")
    suspend fun deleteNote(id: Int)
    @Query("DELETE FROM shoping_list_names WHERE id IS :id")
    suspend fun deleteShopList(id: Int)
    @Query("DELETE FROM shop_list_item WHERE listId LIKE :listId")
    suspend fun deleteAllShopListItem(listId: Int)
    @Query("DELETE FROM library WHERE id LIKE :id")
    suspend fun deleteLibraryItem(id: Int)

    @Update
    suspend fun updateNote(note: NoteItem)
    @Update
    suspend fun updateNoteShopListName(note: ShopingListName)
    @Update
    suspend fun updateShopListItem(note: ShopingListItem)
    @Update
    suspend fun updateLibraryItem(note: LibraryItem)

}