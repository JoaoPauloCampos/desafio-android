package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.picpay.desafio.android.core.InstrumentedTestApplication
import com.picpay.desafio.android.data.local.database.AppDatabase
import com.picpay.desafio.android.data.remote.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.remote.service.UserService
import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.interactor.FetchUsersUseCase
import com.picpay.desafio.android.domain.interactor.GetUsersUseCase
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.user.UserViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


class MainActivityTest {

    private val application = getInstrumentation().targetContext.applicationContext as InstrumentedTestApplication
    private lateinit var userRepository: UserRepository
    private val fetchUsersUseCase = mockk<FetchUsersUseCase>(relaxed = true)
    private val getUsersUseCase = mockk<GetUsersUseCase>(relaxed = true)
    private val userService = mockk<UserService>(relaxed = true)
    private val appDatabase = mockk<AppDatabase>(relaxed = true)

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(userService, appDatabase)
        application.startDependencyInjection(getModules())
        coEvery { userRepository.fetchUsers() } returns Either.Success(Unit)
        coEvery { userRepository.getUserPaginated(scope = any(), pagingConfig = any()) } returns Either.Success(
            flowOf(
                PagingData.from(
                    listOf(
                        mockk(relaxed = true) {
                            every { name } returns  "Jhon"
                            every { userName } returns  "Jhon_01"
                            every { imageUrl } returns  "https://img.jpeg"
                        },
                        mockk(relaxed = true) {
                            every { name } returns  "Rita"
                            every { userName } returns  "rita_01"
                            every { imageUrl } returns  "https://img.jpeg"
                        },
                        mockk(relaxed = true) {
                            every { name } returns  "Petter"
                            every { userName } returns  "petter_01"
                            every { imageUrl } returns  "https://img.jpeg"
                        }
                    )
                )
            )
        )
    }

    private fun getModules(): List<Module> {
        return listOf(
            module {
                factory { fetchUsersUseCase }
                factory { getUsersUseCase }
                viewModel { UserViewModel(get(), get()) }
            }
        )
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = application.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withId(R.id.title)).check(matches(withText(expectedTitle)))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        }
    }
}