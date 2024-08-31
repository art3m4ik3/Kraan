package ru.art3m4ik3.kraan.data.storage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.art3m4ik3.kraan.data.models.AuthResponse
import ru.art3m4ik3.kraan.data.models.Message
import ru.art3m4ik3.kraan.data.models.Product
import ru.art3m4ik3.kraan.data.models.User

interface KraanApi {
    @POST("api/message")
    fun sendMessage(@Body message: Message): Call<Void>

    @POST("api/auth")
    fun authorization(@Body userdata: User): Call<AuthResponse>

    @GET("api/products")
    fun getProducts(): Call<List<Product>>

    @POST("api/products")
    fun createProduct(@Body product: Product, @Header("Authorization") token: String): Call<Void>

    @DELETE("api/products")
    fun deleteProduct(@Body id: Int, @Header("Authorization") token: String): Call<Void>
}