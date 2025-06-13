package com.dicoding.producthub.core.utils

import com.dicoding.producthub.core.data.source.local.entity.ProductEntity
import com.dicoding.producthub.core.data.source.remote.response.ProductResponse
import com.dicoding.producthub.core.domain.model.Product

object DummyData {

    val productResponses = listOf(
        ProductResponse(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            price = 109.95,
            description = "Your perfect pack for everyday use and walks in the forest...",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        ),
        ProductResponse(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            price = 22.3,
            description = "Slim-fitting style, contrast raglan long sleeve...",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
        ),
        ProductResponse(
            id = 3,
            title = "Mens Cotton Jacket",
            price = 55.99,
            description = "Great outerwear jackets for Spring/Autumn/Winter...",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ),
        ProductResponse(
            id = 4,
            title = "Mens Casual Slim Fit",
            price = 15.99,
            description = "The color could be slightly different...",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"
        ),
        ProductResponse(
            id = 5,
            title = "John Hardy Women's Legends Naga Bracelet",
            price = 695.0,
            description = "From our Legends Collection, the Naga was inspired by the mythical water dragon...",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"
        )
    )

    val productEntities = productResponses.map {
        ProductEntity(
            id = it.id,
            title = it.title,
            price = it.price,
            description = it.description,
            category = it.category,
            image = it.image,
            isFavorite = false
        )
    }

    val productDomains = productEntities.map {
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

    val productEntity = productEntities.first()
    val productDomain = productDomains.first()
}
