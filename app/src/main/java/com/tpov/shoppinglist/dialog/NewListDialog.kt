package com.tpov.shoppinglist.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.databinding.NewListDialogBinding

object NewListDialog {
    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.apply {
            tvEditText.setText(name)
            if (name.isEmpty()) {
                bCreate.text = context.getString(R.string.tv_create_list)
                tvNewList.text = context.getString(R.string.b_create)
            } else if (name.isNotEmpty()){
                bCreate.text = context.getString(R.string.tv_update_list)
                tvNewList.text = context.getString(R.string.b_update)

            }
            bCreate.setOnClickListener() {
                val listName = tvEditText.text.toString()
                Log.d("NewListDialog", "bCreate: $listName")

                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog?.dismiss()
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()

    }
    interface Listener{
        fun onClick(name: String)

    }
}