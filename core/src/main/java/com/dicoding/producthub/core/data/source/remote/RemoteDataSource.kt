package com.dicoding.producthub.core.data.source.remote

import com.dicoding.producthub.core.data.source.remote.network.ApiResponse
import com.dicoding.producthub.core.data.source.remote.network.ApiService
import com.dicoding.producthub.core.data.source.remote.response.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllProducts(): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            try {
                val response = apiService.getProducts()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}