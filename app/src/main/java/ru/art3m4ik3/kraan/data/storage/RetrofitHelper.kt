package ru.art3m4ik3.kraan.data.storage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://kraan.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val kraanApi = retrofitBuilder.create(KraanApi::class.java)
}