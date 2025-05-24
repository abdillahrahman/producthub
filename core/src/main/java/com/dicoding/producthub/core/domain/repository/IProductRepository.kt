package com.dicoding.producthub.core.domain.repository

import com.dicoding.producthub.core.data.Resource
import com.dicoding.producthub.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun getFavoriteProducts(): Flow<List<Product>>

    fun setFavoriteProduct(product: Product, state: Boolean)
}