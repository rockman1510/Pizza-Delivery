package com.huylv.presentation_base.mvi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class MVIViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MVIViewModel<String, UiState<String>, UiAction, UiEvent>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = object : MVIViewModel<String, UiState<String>, UiAction, UiEvent>() {
            override fun initState(): UiState<String> = UiState.Loading
            override fun handleAction(action: UiAction) {

            }
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitAction() = runBlockingTest {
        val uiAction = mock<UiAction>()
        viewModel = object : MVIViewModel<String, UiState<String>, UiAction, UiEvent>() {
            override fun initState(): UiState<String> {
                return UiState.Loading
            }

            override fun handleAction(action: UiAction) {
                assertEquals(uiAction, action)
            }
        }
        viewModel.submitAction(uiAction)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitState() = testDispatcher.runBlockingTest {
        val uiState = UiState.Success("test successfully")
        viewModel.submitState(uiState)
        val result = viewModel.uiStateFlow.value
        assert(result is UiState.Success)
        assertEquals(uiState, result)
        assertEquals(uiState.data, (result as UiState.Success).data)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitSingleEvent() = runBlockingTest {
        val uiSingleEvent = mock<UiEvent>()
        viewModel.submitSingleEvent(uiSingleEvent)
        assertEquals(uiSingleEvent, viewModel.singleEventFlow.first())
    }
}