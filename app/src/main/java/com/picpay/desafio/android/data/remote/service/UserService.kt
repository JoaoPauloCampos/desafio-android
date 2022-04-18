package com.picpay.desafio.android.data.remote.service

import com.picpay.desafio.android.data.remote.model.UserRemote
import retrofit2.http.GET


interface UserService {
    @GET("users")
    suspend fun getUsers(): List<UserRemote>
}