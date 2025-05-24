package com.dicoding.producthub.detail

import androidx.lifecycle.ViewModel
import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.core.domain.usecase.ProductUseCase

class DetailProductViewModel (private val productUseCase: ProductUseCase): ViewModel() {
    fun setFavoriteProduct(product: Product, newStatus: Boolean) =
        productUseCase.setFavoriteProduct(product, newStatus)
}