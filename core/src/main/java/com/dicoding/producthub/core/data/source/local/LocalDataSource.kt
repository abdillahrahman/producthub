package com.dicoding.producthub.core.data.source.local

import com.dicoding.producthub.core.data.source.local.entity.ProductEntity
import com.dicoding.producthub.core.data.source.local.room.ProductDao

class LocalDataSource (private val productDao: ProductDao) {

    fun getAllProducts() = productDao.getAllProducts()

    fun getFavoriteProducts() = productDao.getFavoriteProducts()

    suspend fun insertProducts(productsList: List<ProductEntity>) =
        productDao.insertProducts(productsList)

    fun setFavoriteProduct(product: ProductEntity, newState: Boolean) {
        product.isFavorite = newState
        productDao.updateFavoriteProduct(product)
    }
}