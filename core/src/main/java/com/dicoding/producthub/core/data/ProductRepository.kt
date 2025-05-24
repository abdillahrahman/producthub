package com.dicoding.producthub.core.data

import com.dicoding.producthub.core.data.source.local.LocalDataSource
import com.dicoding.producthub.core.data.source.remote.RemoteDataSource
import com.dicoding.producthub.core.data.source.remote.network.ApiResponse
import com.dicoding.producthub.core.data.source.remote.response.ProductResponse
import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.core.domain.repository.IProductRepository
import com.dicoding.producthub.core.utils.AppExecutors
import com.dicoding.producthub.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IProductRepository {

    override fun getAllProducts(): Flow<Resource<List<Product>>> =
        object : NetworkBoundResource<List<Product>, List<ProductResponse>>() {
            override fun loadFromDB(): Flow<List<Product>> {
                return localDataSource.getAllProducts().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ProductResponse>>> =
                remoteDataSource.getAllProducts()

            override suspend fun saveCallResult(data: List<ProductResponse>) {
                val productList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertProducts(productList)
            }
        }.asFlow()

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return localDataSource.getFavoriteProducts().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteProduct(product: Product, state: Boolean) {
        val productEntity = DataMapper.mapDomainToEntity(product)
        appExecutors.diskIO().execute { localDataSource.setFavoriteProduct(productEntity, state) }
    }

}