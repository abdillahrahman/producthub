package com.dicoding.producthub.core.domain.usecase

import com.dicoding.producthub.core.data.Resource
import com.dicoding.producthub.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {
    fun getAllProduct(): Flow<Resource<List<Product>>>
    fun getFavoriteProduct(): Flow<List<Product>>
    fun setFavoriteProduct(product: Product, state: Boolean)

}