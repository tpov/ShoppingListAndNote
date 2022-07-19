package com.tpov.shoppinglist.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tpov.shoppinglist.db.TableRecipeConverter
import java.lang.reflect.Constructor

data class Recipe(
    val aggregateLikes: Int?,
    val analyzedInstructions: List<AnalyzedInstruction>?,
    val cheap: Boolean?,
    val cookingMinutes: Int?,
    val creditsText: String?,
    val cuisines: List<String>?,
    val dairyFree: Boolean?,
    val diets: List<String>?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<ExtendedIngredient>?,
    val gaps: String?,
    val glutenFree: Boolean?,
    val healthScore: Int?,
    val id: Int?,
    val image: String?,
    val imageType: String?,
    val instructions: String?,
    val license: String?,
    val lowFodmap: Boolean?,
    val occasions: List<String>,
    val openLicense: Int?,
    val originalId: String?,
    val preparationMinutes: Int?,
    val pricePerServing: Double?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val sustainable: Boolean?,
    @SerializedName( "title")
    @Expose
    val title2: String?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val veryHealthy: Boolean?,
    val veryPopular: Boolean?,
    val weightWatcherSmartPoints: Int?,

)