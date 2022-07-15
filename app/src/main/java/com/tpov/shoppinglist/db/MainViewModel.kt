package com.tpov.shoppinglist.db

import android.util.Log
import androidx.lifecycle.*
import com.tpov.shoppinglist.ConvertorApiDB
import com.tpov.shoppinglist.api.ApiFactory
import com.tpov.shoppinglist.entities.LibraryItem
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName
import com.tpov.shoppinglist.pojo.Responce
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MainViewModel(database: MainDatabase) : ViewModel() {
    private val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopingListName: LiveData<List<ShopingListName>> = dao.getAllShopListNames().asLiveData()
    val libraryItem = MutableLiveData<List<LibraryItem>>()
    private val compositeDisposable = CompositeDisposable()


    fun getAllItemsFromList(listId: Int) : LiveData<List<ShopingListItem>> {
        return dao.getAllShopListItem(listId).asLiveData()
    }

    fun getAllLibraryItem(name: String) = viewModelScope.launch {
        libraryItem.postValue(dao.getAllLibraryItems(name))
    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNode(note)
    }
    fun insertShopListName(shopListName: ShopingListName) = viewModelScope.launch {
        dao.insertShopListName(shopListName)
    }
    fun insertShopItem(ShopListItem: ShopingListItem) = viewModelScope.launch {
        dao.insertItem(ShopListItem)
        if (!isLibraryItemExists(ShopListItem.name)) dao.insertItemLibrary(LibraryItem(null, ShopListItem.name))
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteLibraryItem(id: Int) = viewModelScope.launch {
        dao.deleteLibraryItem(id)
    }
    fun deleteShopListItem(id: Int) = viewModelScope.launch {
        dao.deleteShopList(id)
        dao.deleteAllShopListItem(listId = id)
    }
    fun cleanShopListItem(id: Int) = viewModelScope.launch {
        dao.deleteAllShopListItem(listId = id)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        Log.d("MainViewModel", "updateNote")
        dao.updateNote(note)
    }
    fun updateNoteShopList(note: ShopingListName) = viewModelScope.launch {
        Log.d("MainViewModel", "updateNoteShopList")
        dao.updateNoteShopListName(note)
    }
    fun updateShopListItem(note: ShopingListItem) = viewModelScope.launch {
        dao.updateShopListItem(note)
    }
    fun updateLibraryItem(note: LibraryItem) = viewModelScope.launch {
        dao.updateLibraryItem(note)
    }

    private suspend fun isLibraryItemExists(name: String) :Boolean {
        return dao.getAllLibraryItems(name).isNotEmpty()
    }

    class MainViewModelFactory(private val database: MainDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED.CAST")
                return MainViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
    }
}