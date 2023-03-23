package com.huylv.data_remote.source

import com.huylv.data_remote.RemoteDataUnitTestUtils
import com.huylv.data_remote.network.flavor.service.FlavorService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteFlavorDataSourceImplTest {

    private val flavorService = mock<FlavorService>()
    private val flavorDataSource = RemoteFlavorDataSourceImpl(flavorService)

    @ExperimentalCoroutinesApi
    @Test
    fun `testGetFlavors - 10`() = runBlockingTest {
        val remoteFlavors = RemoteDataUnitTestUtils().generateFlavorApiModels(10)
        val expectedFlavors = RemoteDataUnitTestUtils().convertToFlavors(remoteFlavors)
        whenever(flavorService.getFlavors()).thenReturn(remoteFlavors)
        val result = flavorDataSource.getFlavors().first()
        assertEquals(expectedFlavors, result)
        assertEquals(10, result.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `testGetFlavors - empty`() = runBlockingTest {
        val remoteFlavors = RemoteDataUnitTestUtils().generateFlavorApiModels(0)
        val expectedFlavors = RemoteDataUnitTestUtils().convertToFlavors(remoteFlavors)
        whenever(flavorService.getFlavors()).thenReturn(remoteFlavors)
        val result = flavorDataSource.getFlavors().first()
        assertEquals(expectedFlavors, result)
        assertEquals(0, result.size)
    }
}