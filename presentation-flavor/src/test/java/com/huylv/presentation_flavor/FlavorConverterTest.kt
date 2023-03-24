package com.huylv.presentation_flavor

import com.huylv.domain.entity.Flavor
import com.huylv.domain.usecase.GetFlavorUseCase
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.mvi.FlavorConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class FlavorConverterTest {

    private val converter = FlavorConverter()

    @Test
    fun testConvertSuccess() {
        val dataList = listOf(
            Flavor("flavor 1", 5F),
            Flavor("flavor 2", 10F),
            Flavor("flavor 3", 6F),
            Flavor("flavor 4", 8F),
            Flavor("flavor 5", 9F)
        )
        val response = GetFlavorUseCase.Response(dataList)
        val result = converter.convertSuccess(response)
        val actualList = dataList.map {
            FlavorModel(it.name, it.price)
        }
        assertEquals(actualList, result)
        assertEquals(actualList.size, result.size)
        assertEquals(actualList[0], result[0])
    }
}