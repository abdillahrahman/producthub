package com.dicoding.producthub.core.utils

import com.dicoding.producthub.core.data.source.local.entity.ProductEntity
import com.dicoding.producthub.core.data.source.remote.response.ProductResponse
import com.dicoding.producthub.core.domain.model.Product

object DataMapper {
    fun mapResponseToEntities(input: List<ProductResponse>): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        input.map {
            val product = ProductEntity(
                id = it.id,
                title = it.title,
                price = it.price,
                description = it.description,
                category = it.category,
                image = it.image,
                isFavorite = false
            )
            productList.add(product)
        }
        return productList
    }

    fun mapEntitiesToDomain(input: List<ProductEntity>): List<Product> =
        input.map {
            Product(
                id = it.id,
                title = it.title,
                price = it.price,
                description = it.description,
                category = it.category,
                image = it.image,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Product) = ProductEntity(
        id = input.id,
        title = input.title,
        price = input.price,
        description = input.description,
        category = input.category,
        image = input.image,
        isFavorite = input.isFavorite
    )

}


