package com.tpov.shoppinglist.db

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.databinding.ListNameItemBinding
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.ShopingListName

class ShopingListAdapter(private val listener: Listener):
    ListAdapter<ShopingListName, ShopingListAdapter.ItemHolder>(ItemComparater()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        Log.d("ShopingListAdapter", "onCreateViewHolder")

        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.d("ShopingListAdapter", "onBindViewHolder")

        holder.setData(getItem(position), listener)
    }

    class ItemComparater : DiffUtil.ItemCallback<ShopingListName>() {
        override fun areItemsTheSame(oldItem: ShopingListName, newItem: ShopingListName): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopingListName, newItem: ShopingListName): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem: ShopingListName, listener: Listener) = with(binding) {
            Log.d("ShopingListAdapter", "setData")

            listNameItemImDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }
            listNameItemImEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }
            listNameItemCardView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }

            tvCounter.backgroundTintList = ColorStateList.valueOf((getPressedColorState(shopListNameItem, binding.root.context)))

            listNameItemTvCounter.text = ("${shopListNameItem.checkenItemsCounter}/${shopListNameItem.allItemCounter}")
            listNameItemTvListName.text = shopListNameItem.name
            listNameItemTvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkenItemsCounter
            pBar.progressTintList = ColorStateList.valueOf(getPressedColorState(shopListNameItem, binding.root.context))
        }

        private fun getPressedColorState(item: ShopingListName, context: Context): Int {
            return  if(item.allItemCounter == item.checkenItemsCounter) {
                ContextCompat.getColor(context, R.color.green)
            } else {
                ContextCompat.getColor(context, R.color.picker_red)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                Log.d("ShopingListAdapter", "create")

                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopList: ShopingListName)
        fun onClickItem(shopList: ShopingListName)
    }
}