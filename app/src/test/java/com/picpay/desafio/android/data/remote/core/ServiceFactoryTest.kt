package com.picpay.desafio.android.data.remote.core

import com.picpay.desafio.android.data.remote.service.UserService
import io.kotlintest.shouldNotBe
import okhttp3.OkHttpClient
import org.junit.Test

class ServiceFactoryTest {

    @Test
    fun `GIVEN OkHttpClient WHEN createWebService() is called THEN return UserService`() {
        // GIVEN
        val client = OkHttpClient.Builder().build()

        // WHEN
        val service = ServiceFactory.createWebService(client) as UserService

        // THEN
        service shouldNotBe null
    }
}