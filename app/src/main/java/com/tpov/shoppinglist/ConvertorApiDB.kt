package com.tpov.shoppinglist

import android.util.Log
import com.tpov.shoppinglist.entities.*
import com.tpov.shoppinglist.pojo.Responce

class ConvertorApiDB {

    fun apiToTable(responce: Responce): ArrayList<Any?> {
        Log.d("RecipeActivity", "Converter.")

        var arrayListRecipe = arrayListOf<Any?>()
        var i = 0
        var j = 0
        var k = 0
        var q = 0

        responce.recipes.forEach { resipe ->
            Log.d("Converter", "recipes")

            arrayListRecipe.add(
                TableRecipe(
                    resipe.id,
                    false,
                    ""
                )
            )

            Log.d("Converter", "TableRecipe")
            arrayListRecipe.add(

                EntityRecipe(
                    null,
                    id = resipe.id,
                    aggregateLikes = resipe.aggregateLikes,
                    cheap = resipe.cheap,
                    cookingMinutes = resipe.cookingMinutes,
                    creditsText = resipe.creditsText,
                    dairyFree = resipe.dairyFree,
                    gaps = resipe.gaps,
                    glutenFree = resipe.glutenFree,
                    healthScore = resipe.healthScore,
                    image = resipe.image,
                    imageType = resipe.imageType,
                    instructions = resipe.instructions,
                    license = resipe.license,
                    lowFodmap = resipe.lowFodmap,
                    openLicense = resipe.openLicense,
                    originalId = resipe.originalId,
                    preparationMinutes = resipe.preparationMinutes,
                    pricePerServing = resipe.pricePerServing,
                    readyInMinutes = resipe.readyInMinutes,
                    servings = resipe.servings,
                    sourceName = resipe.sourceName,
                    sourceUrl = resipe.sourceUrl,
                    spoonacularSourceUrl = resipe.spoonacularSourceUrl,
                    summary = resipe.summary,
                    sustainable = resipe.sustainable,
                    title2 = resipe.title2,
                    vegan = resipe.vegan,
                    vegetarian = resipe.vegetarian,
                    veryHealthy = resipe.veryHealthy,
                    veryPopular = resipe.veryPopular,
                    weightWatcherSmartPoints = resipe.weightWatcherSmartPoints
                )
            )
            i = 0
            Log.d("Converter", "analyzedInstructions")

            resipe.analyzedInstructions?.forEach {
                Log.d("Converter", "EntityRecipe.")

                i++
                arrayListRecipe.add(
                    EntityRecipeAnalyzedInstruction(
                        null, resipe.id, i, it.name
                    )
                )
                j = 0
                Log.d("Converter", "analyzedInstructions")

                it.steps?.forEach { itSteps ->
                    j++
                    arrayListRecipe.add(
                        EntityRecipeStep(
                            null,
                            j,
                            resipe.id!!,
                            i,
                            itSteps.number,
                            itSteps.step,
                        )
                    )
                    k = 0
                    itSteps.equipment?.forEach { itEquipment ->
                        k++
                        arrayListRecipe.add(
                            EntityRecipeEquipment(
                                null,
                                resipe.id,
                                i,
                                j,
                                k,
                                itEquipment.image,
                                itEquipment.localizedName,
                                itEquipment.name
                            )
                        )
                    }
                    k = 0
                    itSteps.ingredients?.forEach { itIngradients ->
                        k++
                        arrayListRecipe.add(
                            EntityRecipeIngredient(
                                null,
                                k,
                                i,
                                j,
                                resipe.id,
                                itIngradients.image,
                                itIngradients.localizedName,
                                itIngradients.name
                            )
                        )
                    }
                    arrayListRecipe.add(
                        EntityRecipeLength(
                            null,
                            resipe.id,
                            i,
                            j,
                            itSteps.length?.number,
                            itSteps.length?.unit
                        )
                    )
                }
            }
            i = 0
            resipe.cuisines?.forEach {
                i++
                arrayListRecipe.add(
                    EntityRecipeCuisines(
                        null,
                        resipe.id,
                        i,
                        it
                    )
                )
            }
            i = 0
            resipe.diets?.forEach {
                i++
                arrayListRecipe.add(
                    EntityRecipeDiets(
                        null,
                        resipe.id,
                        i,
                        it
                    )
                )
            }
            i = 0
            resipe.dishTypes?.forEach {
                i++
                arrayListRecipe.add(
                    EntityRecipeDishTypes(
                        null,
                        resipe.id,
                        i,
                        it
                    )
                )
            }
            i = 0
            resipe.extendedIngredients?.forEach {
                i++
                arrayListRecipe.add(
                    EntityRecipeExtendedIngredient(
                        null,
                        resipe.id,
                        i,
                        it.aisle,
                        it.amount,
                        it.consistency,
                        it.image,
                        it.name,
                        it.nameClean,
                        it.original,
                        it.originalName,
                        it.unit
                    )
                )
                arrayListRecipe.add(
                    EntityRecipeMeasures(
                        null,
                        resipe.id,
                        i
                    )
                )
                it.measures?.metric?.apply {
                    arrayListRecipe.add(
                        EntityRecipeMetric(
                            null,
                            resipe.id,
                            i,
                            this.amount,
                            this.unitLong,
                            this.unitShort
                        )
                    )
                }
                it.measures?.us?.apply {
                    arrayListRecipe.add(
                        EntityRecipeUs(
                            null,
                            resipe.id,
                            i,
                            this.amount,
                            this.unitLong,
                            this.unitShort
                        )
                    )
                }
                j = 0
                it.meta.forEach { itMeta ->
                    j++
                    arrayListRecipe.add(
                        EntityRecipeMeta(
                            null,
                            resipe.id,
                            i,
                            j,
                            itMeta
                        )
                    )

                }
            }
            i = 0
            resipe.occasions.forEach {
                i++
                arrayListRecipe.add(
                    EntityRecipeOccasions(
                        null,
                        resipe.id,
                        i,
                        it
                    )
                )
            }
        }
        Log.d("RecipeActivity", "Converter and")
        return arrayListRecipe
    }
}