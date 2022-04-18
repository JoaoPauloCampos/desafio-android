package com.picpay.desafio.android.domain.interactor

import androidx.paging.PagingData
import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.core.TestCoroutineContextProvider
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {
    private val userRepository = mockk<UserRepository>(relaxed = true, relaxUnitFun = true)
    private val contextProvider = TestCoroutineContextProvider()

    @Before
    fun setup() {
        val users: PagingData<User> = mockk()
        val flow = flowOf(users)
        coEvery {
            userRepository.getUserPaginated(any(), any())
        } returns Either.Success(flow)
    }

    @Test
    fun `WHEN getUsersUseCase is called THEN result must be Success`() = runBlocking {
        // GIVEN
        val getUsersUseCase = GetUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = getUsersUseCase.run(this@runBlocking)

        // THEN
        result.isSuccess shouldBe true
    }

    @Test
    fun `WHEN getUsersUseCase is called with null params THEN result must be Success`() = runBlocking {
        // GIVEN
        val getUsersUseCase = GetUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = getUsersUseCase.run(null)

        // THEN
        result.isFailure shouldBe true
        result.isSuccess shouldBe false
    }

    @Test
    fun `WHEN getUsersUseCase THEN result must be Failure`() = runBlocking {
        // GIVEN
        coEvery { userRepository.getUserPaginated(any()) } returns Either.Failure(mockk(relaxed = true))
        val getUsersUseCase = GetUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = getUsersUseCase.run()

        // THEN
        result.isFailure shouldBe true
        result.isSuccess shouldBe false
    }

    @Test
    fun `WHEN getUsersUseCase with null parameter THEN result must be Failure`() = runBlocking {
        // GIVEN
        coEvery { userRepository.getUserPaginated(any()) } returns Either.Failure(mockk(relaxed = true))
        val getUsersUseCase = GetUsersUseCase(userRepository, contextProvider)

        // WHEN
        val result = getUsersUseCase.run(null)

        // THEN
        result.isFailure shouldBe true
        result.isSuccess shouldBe false
    }
}