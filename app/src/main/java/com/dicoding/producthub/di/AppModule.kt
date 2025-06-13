package com.dicoding.producthub.di

import com.dicoding.producthub.BuildConfig
import com.dicoding.producthub.core.domain.usecase.ProductInteractor
import com.dicoding.producthub.core.domain.usecase.ProductUseCase
import com.dicoding.producthub.detail.DetailProductViewModel
import com.dicoding.producthub.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<String>(qualifier = named("BASE_URL")) { BuildConfig.BASE_URL }
}

val useCaseModule = module {
    factory<ProductUseCase> { ProductInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailProductViewModel(get()) }
}
