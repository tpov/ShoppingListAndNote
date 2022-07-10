package com.tpov.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tpov.shoppinglist.pojo.Responce

@Entity(tableName = "entity_recipe")
data class TableRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "recipes")
    val recipes: Responce,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean,

    @ColumnInfo(name = "date")
    val date: String,
)
