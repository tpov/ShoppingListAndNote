package com.tpov.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpov.shoppinglist.databinding.ActivityShopListBinding
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.db.ShopListItemAdapter
import com.tpov.shoppinglist.dialog.EditListItemDialog
import com.tpov.shoppinglist.entities.LibraryItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName
import com.tpov.shoppinglist.utils.ShareHelper
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {
    private lateinit var binding: ActivityShopListBinding
    private var shopListName: ShopingListName? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null
    private lateinit var textWatcher: TextWatcher

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initRcview()
        listItemObserver()

    }

    private fun libraryItemObserver() {
        mainViewModel.libraryItem.observe(this, {
            val tempShopList = ArrayList<ShopingListItem>()
            it.forEach { item->
                val shopItem = ShopingListItem(
                    item.id,
                    item.name!!,
                    "",
                    false,
                    0,
                    1,
                    ""
                )
                tempShopList.add(shopItem)
            }
            adapter?.submitList(tempShopList)
            if (it.isEmpty())  {
                binding.edEmptyText.visibility = View.VISIBLE
            } else {
                binding.edEmptyText.visibility = View.GONE
            }
        })
    }

    private fun listItemObserver() {
    mainViewModel.getAllItemsFromList(shopListName?.id!!).observe(this, {
        adapter?.submitList(it)
        if (it.isEmpty())  {
            binding.edEmptyText.visibility = View.VISIBLE
        } else {
            binding.edEmptyText.visibility = View.GONE
        }
    })
    /*
        shopListName?.id?.let { it ->
            mainViewModel.getAllItemsFromList(it).observe(this, {
                adapter?.submitList(it)
            }) // тут твой код, только в getAllItemsFromList передавай it
        }*/
}

    private fun initRcview() = with(binding) {
        adapter = ShopListItemAdapter(this@ShopListActivity)
        activityShopListRcView.layoutManager = LinearLayoutManager(this@ShopListActivity)
        activityShopListRcView.adapter = adapter


        swipeListItemAdapter()
    }

    private fun ActivityShopListBinding.swipeListItemAdapter() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter!!.currentList[viewHolder.adapterPosition]
                mainViewModel.deleteShopItem(item.id!!)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(activityShopListRcView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        addNewShopItem(edItem?.text.toString())
        when(item.itemId) {
            R.id.shop_list_menu_delete_list -> {
                mainViewModel.deleteShopList(shopListName?.id!!)
                finish()
            }
            R.id.shop_list_menu_clear_list -> {
                mainViewModel.cleanShopListItem(shopListName?.id!!)
            }
            R.id.shop_list_menu_share_list -> {
                startActivity(Intent.createChooser(
                    ShareHelper.shareShopList(adapter?.currentList!!, shopListName?.name!!),
                    "Share by"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("ShopListActivity", "onCreateOptionMenu")

        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.shop_list_menu_save_item)!!
        saveItem.isVisible = false
        val newItem = menu.findItem(R.id.shop_list_menu_new_item)
        edItem = newItem.actionView.findViewById(R.id.edit_action_layout_edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        edItem?.setText("")
        textWatcher = textWatcher()

        return true
    }

    private fun textWatcher() : TextWatcher {
        return object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.getAllLibraryItem("%$p0%")
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
    }

    private fun addNewShopItem(name: String) {
                                                                Log.d("ShopListActivity", "addNewShopItem")
        if (name.isEmpty()) return
        else {                                                 Log.d("ShopListActivity", "addNewShopItem: text isNotEmpty and save")
            var item = ShopingListItem(
                null,
                name,
                null,
                false,
                shopListName?.id!!,
                0,
                ""
            )
            mainViewModel.insertShopItem(item)
            edItem!!.setText("")
        }
    }

    private fun expandActionView( ) : MenuItem.OnActionExpandListener{
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                saveItem.isVisible = true
                edItem?.addTextChangedListener(textWatcher)
                mainViewModel.getAllItemsFromList(shopListName?.id!!).removeObservers(this@ShopListActivity)
                mainViewModel.getAllLibraryItem("%%")
                libraryItemObserver()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                edItem?.removeTextChangedListener(textWatcher)
                saveItem.isVisible = false
                invalidateOptionsMenu()         //Вернуть меню в первоначальное положение
                mainViewModel.libraryItem.removeObservers(this@ShopListActivity)
                listItemObserver()
                edItem?.setText("")
                return true
            }
        }
    }
    private fun init() {
        shopListName = intent.getSerializableExtra(SHOP_LIST) as ShopingListName
    }

    companion object {
        const val SHOP_LIST = "shop_list"
    }

    override fun editItem(note: ShopingListItem, state: Int) {
        when (state) {
            ShopListItemAdapter.EDIT -> editListItem(note)
            ShopListItemAdapter.CHECK_BOX -> mainViewModel.updateShopListItem(note)
            ShopListItemAdapter.ADD -> addNewShopItem(note.name)
            ShopListItemAdapter.DELETE_LIBRARY -> {
                mainViewModel.deleteLibraryItem(note.id!!)
                mainViewModel.getAllLibraryItem("%${edItem?.text.toString()}%")
            }
           ShopListItemAdapter.EDIT_LIBRARY -> {
               editListItem(note)
               mainViewModel.getAllLibraryItem("%${edItem?.text.toString()}%")
           }
        }
    }

    private fun saveItemCount() {
        var checkedItemCounter = 0
        adapter?.currentList!!.forEach{
            if(it.itemChecked) checkedItemCounter++
        }
        val tempShopListNameItem = shopListName?.copy(
            allItemCounter = adapter?.itemCount!!,
            checkenItemsCounter = checkedItemCounter
        )
        mainViewModel.updateNoteShopList(tempShopListNameItem!!)
    }

    override fun onBackPressed() {
        saveItemCount()
        super.onBackPressed()
    }

    private fun editListItem(note: ShopingListItem) {
        EditListItemDialog.showDialog(binding.root.context as AppCompatActivity, object: EditListItemDialog.Listener {
            override fun onClick(shopListItem: ShopingListItem) {
                if (shopListItem.itemType == 0) mainViewModel.updateShopListItem(shopListItem)
                if (shopListItem.itemType == 1) {
                    mainViewModel.updateLibraryItem(LibraryItem(note.id, note.name))
                    mainViewModel.getAllLibraryItem("%${edItem?.text.toString()}%")
                }
            }
        } , note)
    }
}