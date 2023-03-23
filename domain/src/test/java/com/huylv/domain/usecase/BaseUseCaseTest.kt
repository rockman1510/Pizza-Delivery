package com.huylv.domain.usecase

import com.huylv.domain.entity.Result
import com.huylv.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class BaseUseCaseTest {

    @ExperimentalCoroutinesApi
    private val configuration = BaseUseCase.Configuration(TestCoroutineDispatcher())
    private val request = mock<BaseUseCase.Request>()
    private val response = mock<BaseUseCase.Response>()

    @ExperimentalCoroutinesApi
    private lateinit var useCase: BaseUseCase<BaseUseCase.Request, BaseUseCase.Response>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        useCase = object : BaseUseCase<BaseUseCase.Request, BaseUseCase.Response>(configuration) {
            override suspend fun process(request: Request): Flow<Response> {
                assertEquals(this@BaseUseCaseTest.request, request)
                return flowOf(response)
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteSuccess() = runBlockingTest {
        val result = useCase.execute(request).first()
        assertEquals(Result.Success(response), result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteFlavorException() {
        useCase = object : BaseUseCase<BaseUseCase.Request, BaseUseCase.Response>(configuration) {
            override suspend fun process(request: Request): Flow<Response> {
                assertEquals(this@BaseUseCaseTest.request, request)
                return flow {
                    throw UseCaseException.FlavorException(Throwable())
                }
            }
        }
        runBlockingTest {
            val result = useCase.execute(request).first()
            assertTrue((result as Result.Error).exception is UseCaseException.FlavorException)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteUnknownException() {
        useCase = object : BaseUseCase<BaseUseCase.Request, BaseUseCase.Response>(configuration) {
            override suspend fun process(request: Request): Flow<Response> {
                assertEquals(this@BaseUseCaseTest.request, request)
                return flow {
                    throw UseCaseException.UnknownException(Throwable())
                }
            }
        }
        runBlockingTest {
            val result = useCase.execute(request).first()
            assertTrue((result as Result.Error).exception is UseCaseException.UnknownException)
        }
    }


}