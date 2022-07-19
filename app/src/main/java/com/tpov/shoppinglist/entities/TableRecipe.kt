package com.tpov.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tpov.shoppinglist.pojo.Us

@Entity(tableName = "entity_recipe")
data class TableRecipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean?,

    @ColumnInfo(name = "date")
    var date: String?,
)

@Entity(tableName = "entity_recipe_recipe")
data class EntityRecipe(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    var id: Int?,
    var aggregateLikes: Int?,
    var cheap: Boolean?,
    var cookingMinutes: Int?,
    var creditsText: String?,
    var dairyFree: Boolean?,
    var gaps: String?,
    var glutenFree: Boolean?,
    var healthScore: Int?,
    var image: String?,
    var imageType: String?,
    var instructions: String?,
    var license: String?,
    var lowFodmap: Boolean?,
    var openLicense: Int?,
    var originalId: String?,
    var preparationMinutes: Int?,
    var pricePerServing: Double?,
    var readyInMinutes: Int?,
    var servings: Int?,
    var sourceName: String?,
    var sourceUrl: String?,
    var spoonacularSourceUrl: String?,
    var summary: String?,
    var sustainable: Boolean?,
    var title2: String?,
    var vegan: Boolean?,
    var vegetarian: Boolean?,
    var veryHealthy: Boolean?,
    var veryPopular: Boolean?,
    var weightWatcherSmartPoints: Int?,
)

@Entity(tableName = "entity_recipe_AnalyzedInstruction")
data class EntityRecipeAnalyzedInstruction(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    var idI: Int?,
    var id: Int,
    var name: String?,
)

@Entity(tableName = "entity_recipe_Step")
data class EntityRecipeStep(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val idJ: Int?,
    val id: Int,
    val idI: Int,
    val number: Int?,
    val step: String?
)

@Entity(tableName = "entity_recipe_Length")
data class EntityRecipeLength(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val idJ: Int?,
    val number: Int?,
    val unit: String?
)

@Entity(tableName = "entity_recipe_Equipment")
data class EntityRecipeEquipment(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val idJ: Int?,
    val idK: Int?,
    val image: String?,
    val localizedName: String?,
    val name: String?
)

@Entity(tableName = "entity_recipe_Ingredient")
data class EntityRecipeIngredient(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val idK: Int?,
    val idI: Int?,
    val idJ: Int?,
    val id: Int?,
    val image: String?,
    val localizedName: String?,
    val name: String?
)

@Entity(tableName = "entity_recipe_cuisines")
data class EntityRecipeCuisines(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val cuisines: String?
)

@Entity(tableName = "entity_recipe_diets")
data class EntityRecipeDiets(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val diets: String?
)

@Entity(tableName = "entity_recipe_dishTypes")
data class EntityRecipeDishTypes(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val dishTypes: String?
)

@Entity(tableName = "entity_recipe_ExtendedIngredient")
data class EntityRecipeExtendedIngredient(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val aisle: String?,
    val amount: Double?,
    val consistency: String?,
    val image: String?,
    val name: String?,
    val nameClean: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?
)

@Entity(tableName = "entity_recipe_Measures")
data class EntityRecipeMeasures(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
)

@Entity(tableName = "entity_recipe_Metric")
data class EntityRecipeMetric(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int,
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
)

@Entity(tableName = "entity_recipe_Us")
data class EntityRecipeUs(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
)

@Entity(tableName = "entity_recipe_meta")
data class EntityRecipeMeta(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val idJ: Int?,
    val meta: String?
)

@Entity(tableName = "entity_recipe_occasions")
data class EntityRecipeOccasions(
    @PrimaryKey(autoGenerate = true)
    var varId: Int?,
    val id: Int?,
    val idI: Int?,
    val occasions: String?
)