package com.dicoding.producthub.core.di

import androidx.room.Room
import com.dicoding.producthub.core.data.ProductRepository
import com.dicoding.producthub.core.data.source.local.LocalDataSource
import com.dicoding.producthub.core.data.source.local.room.ProductDatabase
import com.dicoding.producthub.core.data.source.remote.RemoteDataSource
import com.dicoding.producthub.core.data.source.remote.network.ApiService
import com.dicoding.producthub.core.domain.repository.IProductRepository
import com.dicoding.producthub.core.utils.AppExecutors
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ProductDatabase>().productDao() }
    single {
        Room.databaseBuilder(
                androidContext(),
                ProductDatabase::class.java, "Tourism.db"
            ).fallbackToDestructiveMigration(false).build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IProductRepository> {
        ProductRepository(
            get(),
            get(),
            get()
        )
    }
}