package com.tpov.shoppinglist.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.spoonacular.com"
    internal val retrofit = Retrofit.Builder()
        .addConverterFactory (GsonConverterFactory.create ())
        .build()

    val apiService = retrofit.create(ShopApi::class.java)
}



