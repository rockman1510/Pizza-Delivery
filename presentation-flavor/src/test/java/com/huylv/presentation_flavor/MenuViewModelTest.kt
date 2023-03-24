package com.huylv.presentation_flavor

import com.huylv.domain.entity.Result
import com.huylv.domain.usecase.GetFlavorUseCase
import com.huylv.presentation_base.mvi.UiState
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.mvi.FlavorConverter
import com.huylv.presentation_flavor.mvi.MenuUiAction
import com.huylv.presentation_flavor.mvi.MenuViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MenuViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val useCase = mock<GetFlavorUseCase>()
    private val converter = mock<FlavorConverter>()
    private lateinit var viewModel: MenuViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = MenuViewModel(useCase, converter)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testHandleActionLoad() = runBlockingTest {
        assertEquals(UiState.Loading, viewModel.uiStateFlow.value)
        val uiState = mock<UiState<List<FlavorModel>>>()
        val result = mock<Result<GetFlavorUseCase.Response>>()
        whenever(useCase.execute(GetFlavorUseCase.Request)).thenReturn(flowOf(result))
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.handleAction(MenuUiAction.Load)
        assertEquals(uiState, viewModel.uiStateFlow.value)
    }
}
