package com.tpov.shoppinglist.api

import com.tpov.shoppinglist.pojo.Responce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.spoonacular.com/recipes/random?number=1&&apiKey=e81d5dcdb64949deac8393ac89c6045e
interface ApiService {


    @GET("recipes/random")
    suspend fun getFullPriceList(
        @Query(API_KEY) count: String = KEY_COUNT
    ): Responce

    companion object {
        private const val API_KEY = "apiKey"
        private const val KEY_COUNT = "e81d5dcdb64949deac8393ac89c6045e"
    }
}