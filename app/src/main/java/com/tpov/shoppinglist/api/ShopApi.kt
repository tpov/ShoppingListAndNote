package com.tpov.shoppinglist.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopApi {
    @GET("recipes/random")
    fun getRandomRecept(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "e81d5dcdb64949deac8393ac89c6045e",
        @Query(LIMITE_LICENCE) limit: Boolean = false,
        @Query(TAGS) rags: String = "",
        @Query(NUMBER) num: Int = 5
    ): Single<Data>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val LIMITE_LICENCE = "limitLicense"
        private const val TAGS = "tags"
        private const val NUMBER = "number"
    }

}