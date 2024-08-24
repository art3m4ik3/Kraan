package ru.art3m4ik3.kraan.data.storage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.art3m4ik3.kraan.data.models.Message

interface KraanApi {
    @POST("api/message")
    fun sendMessage(@Body message: Message): Call<Void>
}