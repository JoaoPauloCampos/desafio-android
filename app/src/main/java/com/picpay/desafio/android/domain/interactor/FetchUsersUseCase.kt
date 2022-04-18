package com.picpay.desafio.android.domain.interactor

import com.picpay.desafio.android.data.remote.core.PicPayError
import com.picpay.desafio.android.domain.core.CoroutineContextProvider
import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.core.UseCase
import com.picpay.desafio.android.domain.repository.UserRepository

class FetchUsersUseCase(
    private val repository: UserRepository,
    contextProvider: CoroutineContextProvider
) : UseCase<Unit, Unit>(contextProvider) {
    override suspend fun run(params: Unit?): Either<Unit, PicPayError> = repository.fetchUsers()
}