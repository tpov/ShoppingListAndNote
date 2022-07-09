package com.tpov.shoppinglist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Step(
    private val equipment: List<Equipment>,
    @SerializedName("ingredients")
    @Expose
    private val ingredients: List<Ingredient>,
    private val length: Length,
    private val number: Int,
    private val step: String
) {
    fun getEquipment(): List<Equipment> {
        return equipment
    }
}