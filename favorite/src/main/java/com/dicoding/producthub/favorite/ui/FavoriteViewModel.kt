package com.dicoding.producthub.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.producthub.core.domain.usecase.ProductUseCase

class FavoriteViewModel(productUseCase: ProductUseCase) : ViewModel() {
    val favoriteProduct = productUseCase.getFavoriteProduct().asLiveData()
}