package com.dicoding.producthub.core.di

import androidx.room.Room
import com.dicoding.producthub.core.data.ProductRepository
import com.dicoding.producthub.core.data.source.local.LocalDataSource
import com.dicoding.producthub.core.data.source.local.room.ProductDatabase
import com.dicoding.producthub.core.data.source.remote.RemoteDataSource
import com.dicoding.producthub.core.data.source.remote.network.ApiService
import com.dicoding.producthub.core.domain.repository.IProductRepository
import com.dicoding.producthub.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ProductDatabase>().productDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
                    androidContext(),
                    ProductDatabase::class.java, "Product.db"
                ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
    }
}

val networkModule = module {
    single {
        val hostname = "fakestoreapi.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/zD9yu/Qi8clBN8AEV64BojX2yXwIfuuhekZATURPmDQ=")
            .build()
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val baseUrl: String = get(qualifier = named("BASE_URL"))
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
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