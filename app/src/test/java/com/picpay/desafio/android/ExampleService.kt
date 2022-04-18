package com.picpay.desafio.android

import com.picpay.desafio.android.data.remote.model.UserRemote
import com.picpay.desafio.android.data.remote.service.UserService

class ExampleService(
    private val service: UserService
) {

    fun example(): List<UserRemote> = emptyList()/*{
        *//*val users = service.getUsers().execute()

        return users.body() ?: emptyList()*//*
    }*/
}