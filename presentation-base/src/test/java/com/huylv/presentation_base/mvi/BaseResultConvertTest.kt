package com.huylv.presentation_base.mvi

import com.huylv.domain.entity.UseCaseException
import com.huylv.domain.entity.Result
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BaseResultConvertTest {

    private val converter = object : BaseResultConverter<String, String>() {
        override fun convertSuccess(data: String): String {
            return "result: $data"
        }
    }

    @Test
    fun testResultConvertSuccess() {
        val data = "this is a success data"
        val result = converter.convert(Result.Success(data))
        assertEquals(UiState.Success("result: $data"), result)
    }

    @Test
    fun testResultConvertError() {
        val errorMessage = "this is an error test message"
        val exception = mock<UseCaseException>()
        whenever(exception.message).thenReturn(errorMessage)
        val result = converter.convert(Result.Error(exception))
        assertEquals(UiState.Error(errorMessage), result)
    }
}