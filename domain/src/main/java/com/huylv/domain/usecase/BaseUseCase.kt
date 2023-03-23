package com.huylv.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import com.huylv.domain.entity.Result
import com.huylv.domain.entity.UseCaseException
import kotlinx.coroutines.flow.map

abstract class BaseUseCase<INPUT : BaseUseCase.Request, OUTPUT : BaseUseCase.Response>(private val configuration: Configuration) {
    suspend fun execute(request: INPUT): Flow<Result<OUTPUT>> {
        return process(request).map {
            Result.Success(it) as Result<OUTPUT>
        }.flowOn(configuration.dispatcher)
            .catch {
                emit(Result.Error(UseCaseException.createFromThrowable(it)))
            }
    }

    internal abstract suspend fun process(request: INPUT): Flow<OUTPUT>

    class Configuration(val dispatcher: CoroutineDispatcher)

    interface Request
    interface Response
}