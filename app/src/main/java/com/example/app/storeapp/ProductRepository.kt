package com.example.app.storeapp

import retrofit2.Response

class ProductRepository {
    suspend fun getAllProducts() : Response<List<Product>>{
        return RetrofitInstance.api.getProductsList()
    }
}