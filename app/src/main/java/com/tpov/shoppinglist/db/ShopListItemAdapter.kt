package com.tpov.shoppinglist.db

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.FileUtil.copy
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.databinding.ShopLibraryNameBinding
import com.tpov.shoppinglist.databinding.ShopListNameBinding
import com.tpov.shoppinglist.entities.LibraryItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName
import java.util.Collections.copy

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopingListItem, ShopListItemAdapter.ItemHolder>(ItemComporater()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        Log.d("ShopListItemAdapter", "onCreateViewHolder: viewType = $viewType")
        return if (viewType == 0) ItemHolder.createShopItem(parent)
            else ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.d("ShopListItemAdapter", "onBindViewHolder: ${getItem(position).itemType}")
        if (getItem(position).itemType == 0) {
            holder.setShopItem(getItem(position), listener)
        } else {
            holder.setLibraryItem(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemComporater : DiffUtil.ItemCallback<ShopingListItem>() {
        override fun areItemsTheSame(oldItem: ShopingListItem, newItem: ShopingListItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: ShopingListItem,
            newItem: ShopingListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun setShopItem(noteShopItem: ShopingListItem, listener: Listener) {
            val binding = ShopListNameBinding.bind(view)
            binding.apply {
                shopListNameTvName.text = noteShopItem.name
                shopListNameTvInfo.text = noteShopItem.itemInfo
                shopListNameTvInfo.visibility = infoVisibility(noteShopItem)
                checkBox.isChecked =
                    noteShopItem.itemChecked
                    setPaintFlagAndColor(binding)

                shopListNameIcEdit.setOnClickListener {
                    listener.editItem(noteShopItem, EDIT)
                }
                checkBox.setOnClickListener {
                    listener.editItem(noteShopItem.copy(itemChecked = checkBox.isChecked), CHECK_BOX)
                }
            }
        }

        private fun setPaintFlagAndColor(binding: ShopListNameBinding) {
            binding.apply {
                if(checkBox.isChecked) {
                    shopListNameTvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    shopListNameTvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    shopListNameTvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.light_grey))
                    shopListNameTvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.light_grey))
                } else {
                    shopListNameTvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    shopListNameTvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    shopListNameTvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    shopListNameTvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                }
            }
        }
        fun setLibraryItem(noteLibraryItem: ShopingListItem, listener: Listener) {
            val binding = ShopLibraryNameBinding.bind(view)
            binding.apply {
                shopLibraryNameBDelete.setOnClickListener {
                    listener.editItem(noteLibraryItem, DELETE_LIBRARY)
                }
                shopLibraryNameBEdit.setOnClickListener {
                    listener.editItem(noteLibraryItem, EDIT_LIBRARY)
                }
                itemView.setOnClickListener {
                    listener.editItem(noteLibraryItem, ADD)
                }
                shopLibraryNameTvName.text = noteLibraryItem.name
            }
        }

        private fun infoVisibility(shopListItem: ShopingListItem): Int {
            return if (shopListItem.itemInfo.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        companion object {

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_name, parent, false)
                )
            }

            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_name, parent, false)
                )
            }
        }
    }

    interface Listener {
        fun editItem(note: ShopingListItem, state: Int)
    }

    companion object {
        const val EDIT = 0
        const val CHECK_BOX = 1
        const val DELETE_LIBRARY = 2
        const val EDIT_LIBRARY = 3
        const val ADD = 4
    }
}