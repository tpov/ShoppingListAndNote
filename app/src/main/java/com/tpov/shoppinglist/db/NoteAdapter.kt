package com.tpov.shoppinglist.db

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.databinding.NoteListItemBinding
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.entities.ShopingListName
import com.tpov.shoppinglist.utils.HtmlManager
import com.tpov.shoppinglist.utils.TimeManager

class NoteAdapter(private val listener: Listener, val defPref: SharedPreferences) :
    ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparater()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener, defPref)

    }

    class ItemComparater : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)

        fun setData(note: NoteItem, listener: NoteAdapter.Listener, defPref: SharedPreferences) = with(binding) {
            Log.d("NoteAdapter", "setData")

            tvTitle.text = note.title
            tvDescription.text = HtmlManager.getFormatHtml(note.content).trim()
            tvTime.text = TimeManager.getTimeFormat(note.time, defPref)
            imDelete.setOnClickListener {
                listener.deleteItem(note.id!!)
            }
            itemView.setOnClickListener {
                Log.d("NoteAdapter_itemView", "itemView.setOnClickListener")
                listener.onClickItem(note)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                Log.d("NoteAdapter", "create")

                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.note_list_item, parent, false)
                )
            }
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(note: NoteItem)
    }
}