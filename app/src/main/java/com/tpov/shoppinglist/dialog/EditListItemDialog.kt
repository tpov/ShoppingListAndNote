package com.tpov.shoppinglist.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.tpov.shoppinglist.databinding.EditListItemDialogBinding
import com.tpov.shoppinglist.entities.ShopingListItem

object EditListItemDialog {
    fun showDialog(context: Context, listener: Listener, shopListItem: ShopingListItem) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.apply {
            if (shopListItem.itemType == 1) {
                etItemInfo.visibility = View.GONE
                tvWordItemInfo.visibility = View.GONE
            }
            editTextTextPersonName.setText(shopListItem.name)
            etItemInfo.setText(shopListItem.itemInfo)
            tvWordItemInfo.text = "0/0"

            bUpdateItem.setOnClickListener {
                if (editTextTextPersonName.text.isNotEmpty()) {
                    var etInfo :String? = if(etItemInfo.text.isEmpty()) {
                        null
                    } else etItemInfo.text.toString()
                    listener.onClick(shopListItem.copy(name = editTextTextPersonName.text.toString(), itemInfo = etInfo))
                }

                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(shopListItem: ShopingListItem)
    }
}