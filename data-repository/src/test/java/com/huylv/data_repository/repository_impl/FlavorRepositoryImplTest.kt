package com.huylv.data_repository.repository_impl

import com.huylv.data_repository.DataRepoUnitTestUtils
import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.domain.entity.Flavor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FlavorRepositoryImplTest {

    private val remoteFlavorDataSource = mock<RemoteFlavorDataSource>()
    private val flavorRepositoryImpl = FlavorRepositoryImpl(remoteFlavorDataSource)

    @ExperimentalCoroutinesApi
    @Test
    fun `testGetFlavors - 10`() = runBlockingTest {
        val flavors = DataRepoUnitTestUtils().generateFlavors(10)
        whenever(remoteFlavorDataSource.getFlavors()).thenReturn(flowOf(flavors))
        val result = flavorRepositoryImpl.getFlavors().first()
        assertEquals(flavors, result)
        assertEquals(10, result.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `testGetFlavors - empty`() = runBlockingTest {
        val flavors = DataRepoUnitTestUtils().generateFlavors(0)
        whenever(remoteFlavorDataSource.getFlavors()).thenReturn(flowOf(flavors))
        val result = flavorRepositoryImpl.getFlavors().first()
        assertEquals(flavors, result)
        assertEquals(0, result.size)
    }
}