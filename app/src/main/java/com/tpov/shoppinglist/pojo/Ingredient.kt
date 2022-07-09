package com.tpov.shoppinglist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ingredient(
    private val id: Int,
    private val image: String,
    private val localizedName: String,
    @SerializedName("name")
    @Expose
    private val name: String
) {
    fun getName(): String {
        return name
    }
}