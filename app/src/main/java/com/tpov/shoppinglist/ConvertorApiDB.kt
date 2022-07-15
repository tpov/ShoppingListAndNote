package com.tpov.shoppinglist

import android.util.Log
import com.tpov.shoppinglist.entities.*
import com.tpov.shoppinglist.pojo.Responce

class ConvertorApiDB {

    fun apiToTable(responce: Responce): ArrayList<Any?> {

        var arrayListRecipe = arrayListOf<Any?>()
        var i = 0
        var j = 0
        var k = 0
        var q = 0

        responce.recipes.forEach { resipe ->
            Log.d("RecipeActivity", "recipeApi forEach, it = $resipe")
            arrayListRecipe.add(
                TableRecipe(
                    resipe.id,
                    false,
                    ""
                )
            )
            arrayListRecipe.add(

                EntityRecipe(
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
            resipe.analyzedInstructions?.forEach {
                i++
                arrayListRecipe.add(EntityRecipeAnalyzedInstruction(resipe.id, i, it.name))
                j = 0
                it.steps?.forEach { itSteps ->
                    j++
                    arrayListRecipe.add(
                        EntityRecipeStep(
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
                        resipe.id,
                        i
                    )
                )
                it.measures?.metric?.apply {
                    arrayListRecipe.add(
                        EntityRecipeMetric(
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
                        resipe.id,
                        i,
                        it
                    )
                )
            }
        }
        Log.d("RecipeActivity", "arrayListRecipe = $arrayListRecipe")
        return arrayListRecipe
    }
}