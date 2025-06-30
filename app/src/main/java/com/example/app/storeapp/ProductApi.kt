package com.example.app.storeapp


import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProductsList() : Response<List<Product>>
}