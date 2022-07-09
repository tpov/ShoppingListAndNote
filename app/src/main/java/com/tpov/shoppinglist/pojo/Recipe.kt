package com.tpov.shoppinglist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("aggregateLikes")
    @Expose
    private val aggregateLikes: Int,

    private val analyzedInstructions: List<AnalyzedInstruction>,
    private val cheap: Boolean,
    private val cookingMinutes: Int,

    private val creditsText: String,

    private val cuisines: List<Any>,
    private val dairyFree: Boolean,
    private val diets: List<String>,
    private val dishTypes: List<String>,
    private val extendedIngredients: List<ExtendedIngredient>,
    private val gaps: String,
    private val glutenFree: Boolean,
    private val healthScore: Int,
    private val id: Int,
    private val image: String,
    private val imageType: String,
    private val instructions: String,
    private val license: String,
    private val lowFodmap: Boolean,
    private val occasions: List<Any>,
    private val openLicense: Int,
    private val originalId: Any,
    private val preparationMinutes: Int,
    private val pricePerServing: Double,
    private val readyInMinutes: Int,
    private val servings: Int,
    private val sourceName: String,
    private val sourceUrl: String,
    private val spoonacularSourceUrl: String,
    private val summary: String,
    private val sustainable: Boolean,
    private val title: String,
    private val vegan: Boolean,
    private val vegetarian: Boolean,
    private val veryHealthy: Boolean,
    private val veryPopular: Boolean,
    private val weightWatcherSmartPoints: Int
) {
    fun getAgregateLikes(): Int {
        return aggregateLikes
    }
    fun getanalyzedInstructions(): List<AnalyzedInstruction> {
        return analyzedInstructions
    }
    fun getcheap(): Boolean {
        return cheap
    }
    fun getcookingMinutes(): Int {
        return cookingMinutes
    }
    fun getcreditsText(): String {
        return creditsText
    }
    fun getcuisines(): List<Any> {
        return cuisines
    }
    fun getdairyFree(): Boolean? {
        return dairyFree
    }/*
    fun getdiets(): >? {
        return diets
    }
    fun getdishTypes(): >? {
        return dishTypes
    }
    fun getextendedIngredients(): >? {
        return extendedIngredients
    }*/
    fun getgaps(): String? {
        return gaps
    }
    fun getglutenFree(): Boolean? {
        return glutenFree
    }
    fun gethealthScore(): Int? {
        return healthScore
    }
    fun getid(): Int? {
        return id
    }
    fun getimage(): String? {
        return image
    }
    fun getimageType(): String? {
        return imageType
    }
    fun getinstructions(): String? {
        return instructions
    }
    fun getlicense(): String? {
        return license
    }
    fun getlowFodmap(): Boolean? {
        return lowFodmap
    }/*
    fun getoccasions(): >? {
        return occasions
    }*/
    fun getopenLicense(): Int? {
        return openLicense
    }
    fun getoriginalId(): Any? {
        return originalId
    }
    fun getpreparationMinutes(): Int? {
        return preparationMinutes
    }
    fun getpricePerServing(): Double? {
        return pricePerServing
    }
    fun getreadyInMinutes(): Int? {
        return readyInMinutes
    }
    fun getservings(): Int? {
        return servings
    }
    fun getsourceName(): String? {
        return sourceName
    }
    fun getsourceUrl(): String? {
        return sourceUrl
    }
    fun getspoonacularSourceUrl(): String? {
        return spoonacularSourceUrl
    }
    fun getsummary(): String? {
        return summary
    }
    fun getsustainable(): Boolean? {
        return sustainable
    }
    fun gettitle(): String? {
        return title
    }
    fun getvegan(): Boolean? {
        return vegan
    }
    fun getvegetarian(): Boolean? {
        return vegetarian
    }
    fun getveryHealthy(): Boolean? {
        return veryHealthy
    }
    fun getveryPopular(): Boolean? {
        return veryPopular
    }
    fun getweightWatcherSmartPoints(): Int? {
        return weightWatcherSmartPoints
    }
}