package com.tpov.shoppinglist.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.util.*
import java.util.Arrays.asList
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class TableRecipeConverter {

    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    fun fromRecipe(recipe: List<String>): String = recipe.stream().collect(Collectors.joining(","))
    @TypeConverter
    fun toRecipe(data: String): MutableList<List<String>> = Arrays.asList(data.split(","))
}