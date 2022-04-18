package com.picpay.desafio.android.domain.interactor

import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.core.TestCoroutineContextProvider
import com.picpay.desafio.android.domain.repository.UserRepository
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchUsersUseCaseTest {

    private val userRepository = mockk<UserRepository>(relaxed = true)
    private val contextProvider = TestCoroutineContextProvider()

    @Before
    fun setUp() {
        coEvery { userRepository.fetchUsers() } returns Either.Success(mockk(relaxed = true))
    }

    @Test
    fun `WHEN FetchUsersUseCase is called THEN result must be Success`() = runBlocking {
        // GIVEN
        val fetchUsersUseCase = FetchUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = fetchUsersUseCase.run()

        // THEN
        result.isFailure shouldBe false
        result.isSuccess shouldBe true
    }

    @Test
    fun `WHEN FetchUsersUseCase is called with null params THEN result must be Success`() = runBlocking {
        // GIVEN
        val fetchUsersUseCase = FetchUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = fetchUsersUseCase.run(null)

        // THEN
        result.isFailure shouldBe false
        result.isSuccess shouldBe true
    }

    @Test
    fun `WHEN FetchUsersUseCase THEN result must be Failure`() = runBlocking {
        // GIVEN
        coEvery { userRepository.fetchUsers() } returns Either.Failure(mockk(relaxed = true))
        val fetchUsersUseCase = FetchUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = fetchUsersUseCase.run()

        // THEN
        result.isFailure shouldBe true
        result.isSuccess shouldBe false
    }

    @Test
    fun `WHEN FetchUsersUseCase with null parameter THEN result must be Failure`() = runBlocking {
        // GIVEN
        coEvery { userRepository.fetchUsers() } returns Either.Failure(mockk(relaxed = true))
        val fetchUsersUseCase = FetchUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = fetchUsersUseCase.run(null)

        // THEN
        result.isFailure shouldBe true
        result.isSuccess shouldBe false
    }
}