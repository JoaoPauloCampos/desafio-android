package com.picpay.desafio.android.data.remote.core

import com.picpay.desafio.android.data.remote.service.UserService
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RequestWrapperKtTest {

    private val userService = mockk<UserService>(relaxed = true)
    @Test
    fun `GIVEN userService WHEN request wrapper is called with success THEN result must be Either Success`() = runBlocking {
        // GIVEN
        coEvery { userService.getUsers() } returns mockk(relaxed = true)

        // WHEN
        val result = requestWrapper {
            userService.getUsers()
        }

        //THEN
        result.isSuccess shouldBe true
    }

    @Test
    fun `GIVEN userService WHEN request wrapper is called with error THEN result must be Either Failure`() = runBlocking {
        // GIVEN
        coEvery { userService.getUsers() } throws Exception("Any Exception")

        // WHEN
        val result = requestWrapper {
            userService.getUsers()
        }

        //THEN
        result.isFailure shouldBe true
    }
}