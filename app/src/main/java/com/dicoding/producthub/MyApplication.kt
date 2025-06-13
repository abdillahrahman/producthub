package com.dicoding.producthub

import android.app.Application
import com.dicoding.producthub.core.di.databaseModule
import com.dicoding.producthub.core.di.networkModule
import com.dicoding.producthub.core.di.repositoryModule
import com.dicoding.producthub.di.appModule
import com.dicoding.producthub.di.useCaseModule
import com.dicoding.producthub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}