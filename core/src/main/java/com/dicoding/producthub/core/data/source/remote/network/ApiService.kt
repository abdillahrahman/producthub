package com.dicoding.producthub.core.data.source.remote.network

import com.dicoding.producthub.core.data.source.remote.response.ProductResponse
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductResponse>
}