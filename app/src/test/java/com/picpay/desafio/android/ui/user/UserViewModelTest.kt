package com.picpay.desafio.android.ui.user

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.picpay.desafio.android.domain.interactor.FetchUsersUseCase
import com.picpay.desafio.android.domain.interactor.GetUsersUseCase
import com.picpay.desafio.android.domain.model.User
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class UserViewModelTest {

    val fetchUsersUseCase: FetchUsersUseCase = mockk(relaxed = true, relaxUnitFun = true)
    val getUsersUseCase: GetUsersUseCase = mockk(relaxed = true, relaxUnitFun = true)
    private val exception = Exception("Any Exception")

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)
    private val viewModel: UserViewModel by lazy {
        UserViewModel(fetchUsersUseCase = fetchUsersUseCase, getUsersUseCase = getUsersUseCase)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `GIVEN userViewModel WHEN fetchUsers is called with Success THEN userLiveData value must be valid value`() =
        testCoroutineScope.runBlockingTest {
            // GIVEN
            coEvery {
                fetchUsersUseCase(
                    scope = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            } coAnswers { lambda<(Unit) -> Unit>().invoke(Unit) }

            val userData = mockk<PagingData<User>>()
            coEvery {
                getUsersUseCase(
                    scope = any(),
                    params = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            } coAnswers {
                lambda<((Flow<PagingData<User>>) -> Unit)>().invoke(flowOf(userData))
            }

            // WHEN
            // viewModel started

            //THEN
            viewModel.usersLiveData.value shouldNotBe null
            coVerify(exactly = 1) {
                getUsersUseCase(
                    scope = any(),
                    params = any(),
                    onError = any(),
                    onSuccess = any()
                )
            }
            coVerify(exactly = 1) {
                fetchUsersUseCase(
                    scope = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            }
        }

    @Test
    fun `GIVEN userViewModel WHEN fetchUsers is called with Success but fetchUser is Failed THEN userLiveData value must be null`() =
        testCoroutineScope.runBlockingTest {
            // GIVEN
            coEvery {
                fetchUsersUseCase(
                    scope = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            } coAnswers { lambda<(Unit) -> Unit>().invoke(Unit) }

            val userData = mockk<PagingData<User>>()
            coEvery {
                getUsersUseCase(
                    scope = any(),
                    params = any(),
                    onError = captureLambda(),
                    onSuccess = any()
                )
            } coAnswers {
                lambda<((Exception) -> Unit)>().invoke(exception)
            }

            // WHEN
            // viewModel started

            //THEN
            viewModel.usersLiveData.value shouldBe null
            coVerify(exactly = 1) {
                getUsersUseCase(
                    scope = any(),
                    params = any(),
                    onError = any(),
                    onSuccess = any()
                )
            }
            coVerify(exactly = 1) {
                fetchUsersUseCase(
                    scope = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            }
            verify(exactly = 1) { Log.d("UserViewModel", "getUsers()", exception) }
        }

    @Test
    fun `GIVEN userViewModel WHEN fetchUsers is called with Error THEN userLiveData value must be null`() =
        testCoroutineScope.runBlockingTest {
            // GIVEN
            coEvery {
                fetchUsersUseCase(
                    scope = any(),
                    onError = captureLambda(),
                    onSuccess = any()
                )
            } coAnswers { lambda<(Exception) -> Unit>().invoke(exception) }

            //WHEN
            // viewModel started

            //THEN
            viewModel.usersLiveData.value shouldBe null
            coVerify(exactly = 0) {
                getUsersUseCase(
                    scope = any(),
                    params = any(),
                    onError = any(),
                    onSuccess = any()
                )
            }
            coVerify(exactly = 1) {
                fetchUsersUseCase(
                    scope = any(),
                    onError = any(),
                    onSuccess = captureLambda()
                )
            }
            verify(exactly = 1) { Log.d("UserViewModel", "fetchUsers()", exception) }
        }
}