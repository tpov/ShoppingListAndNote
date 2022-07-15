package com.tpov.shoppinglist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Int?,
    val image: String?,
    val localizedName: String?,
    val name: String?
)