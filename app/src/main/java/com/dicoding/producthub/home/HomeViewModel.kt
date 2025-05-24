package com.dicoding.producthub.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.producthub.core.domain.usecase.ProductUseCase

class HomeViewModel(productUseCase: ProductUseCase) : ViewModel() {
    val product = productUseCase.getAllProduct().asLiveData()
}