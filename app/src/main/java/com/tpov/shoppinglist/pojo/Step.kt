package com.tpov.shoppinglist.pojo

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Step(
   val equipment: List<Equipment>?,
   val ingredients: List<Ingredient>?,
   val length: Length?,
   val number: Int?,
   val step: String?
)