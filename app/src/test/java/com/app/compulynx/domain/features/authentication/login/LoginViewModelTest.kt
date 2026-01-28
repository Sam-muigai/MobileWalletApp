package com.app.compulynx.domain.features.authentication.login

import com.app.compulynx.core.testing.repositories.FakeAuthRepository
import com.app.compulynx.features.authentication.login.LoginScreenEffect
import com.app.compulynx.features.authentication.login.LoginScreenEvent
import com.app.compulynx.features.authentication.login.LoginScreenViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var fakeAuthRepository: FakeAuthRepository
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeAuthRepository = FakeAuthRepository()
        viewModel = LoginScreenViewModel(fakeAuthRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when valid credentials are entered, form becomes valid`() {
        // Act: Enter valid Customer ID and PIN
        viewModel.handleEvent(LoginScreenEvent.OnCustomerIdChange("CUST1001"))
        viewModel.handleEvent(LoginScreenEvent.OnPinChange("1111"))

        // Assert
        val state = viewModel.state.value
        assertEquals("CUST1001", state.customerId)
        assertEquals("1111", state.pin)
        assertTrue(state.isFormValid)
    }

    @Test
    fun `when login is clicked and successful, loading hides and navigation effect is sent`() = runTest {
        val results = mutableListOf<LoginScreenEffect>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.effect.collect { results.add(it) }
        }

        // Act
        viewModel.handleEvent(LoginScreenEvent.OnCustomerIdChange("CUST1001"))
        viewModel.handleEvent(LoginScreenEvent.OnPinChange("1111"))
        fakeAuthRepository.setShouldReturnError(false)

        viewModel.handleEvent(LoginScreenEvent.OnLoginButtonClick)

        // Verify loading starts
        assertTrue("Loading state should be active", viewModel.state.value.isLoading)

        advanceUntilIdle()

        // Assert loading stopped
        assertFalse("Loading state should be inactive", viewModel.state.value.isLoading)

        // Verify NavigateToHome effect is sent
        assertTrue(
            "Expected NavigateToHome in $results",
            results.any { it is LoginScreenEffect.NavigateToHome }
        )
    }

}