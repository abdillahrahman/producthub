package com.dicoding.producthub.core.domain.usecase

import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.core.domain.repository.IProductRepository

class ProductInteractor(private val productRepository: IProductRepository) : ProductUseCase {

    override fun getAllProduct() = productRepository.getAllProducts()

    override fun getFavoriteProduct() = productRepository.getFavoriteProducts()

    override fun setFavoriteProduct(product: Product, state: Boolean) =
        productRepository.setFavoriteProduct(product, state)
}