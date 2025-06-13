package com.dicoding.producthub.core.data

import com.dicoding.producthub.core.data.source.local.LocalDataSource
import com.dicoding.producthub.core.data.source.remote.RemoteDataSource
import com.dicoding.producthub.core.utils.AppExecutors
import com.dicoding.producthub.core.utils.DummyData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class ProductRepositoryTest : KoinTest {

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private val appExecutors: AppExecutors = mock()

    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        productRepository = ProductRepository(remoteDataSource, localDataSource, appExecutors)
    }

    @Test
    fun `getFavoriteProducts should return data from localDataSource`()  {
        val dummyProducts = listOf(DummyData.productEntity)
        whenever(localDataSource.getFavoriteProducts()).thenReturn(flowOf(dummyProducts))

        val result = runBlocking {
            productRepository.getFavoriteProducts().first()
        }

        assertEquals(DummyData.productDomain, result.first())
        verify(localDataSource).getFavoriteProducts()
    }
}
