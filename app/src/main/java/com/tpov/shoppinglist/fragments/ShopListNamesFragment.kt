package com.tpov.shoppinglist.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.shoppinglist.MainApp
import com.tpov.shoppinglist.ShopListActivity
import com.tpov.shoppinglist.databinding.FragmentShopListNamesBinding
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.db.ShopingListAdapter
import com.tpov.shoppinglist.dialog.DeleteDialog
import com.tpov.shoppinglist.dialog.NewListDialog
import com.tpov.shoppinglist.entities.ShopingListName
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi as InternalCoroutinesApi1

@kotlinx.coroutines.InternalCoroutinesApi
class ShopListNamesFragment : BaseFragment(), ShopingListAdapter.Listener {
    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShopingListAdapter

    @InternalCoroutinesApi1
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        Log.d("ShopListNamesFragment", "onClickNew")

        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                val shopListName = ShopingListName(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shopListName)
            }
        }, "")
    }

    @OptIn(kotlinx.coroutines.InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopingListAdapter(this@ShopListNamesFragment)
        rcView.adapter = adapter
    }

    @kotlinx.coroutines.InternalCoroutinesApi
    private fun observer() {
        mainViewModel.allShopingListName.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener {
            override fun onClick() {
                mainViewModel.deleteShopListItem(id)
            }
        })
    }

    override fun editItem(shopList: ShopingListName) {
        NewListDialog.showDialog(context as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                mainViewModel.updateNoteShopList(shopList.copy(name = name))
            }
        }, shopList.name)
    }

    override fun onClickItem(shopListName: ShopingListName) {
        val i = Intent(activity, ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST, shopListName)
        }
        startActivity(i)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }

}