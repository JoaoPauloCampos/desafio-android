package com.picpay.desafio.android.domain.interactor

import androidx.paging.PagingData
import com.picpay.desafio.android.data.remote.core.PicPayError
import com.picpay.desafio.android.domain.core.CoroutineContextProvider
import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.core.UseCase
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class GetUsersUseCase(
    private val repository: UserRepository,
    contextProvider: CoroutineContextProvider
) : UseCase<Flow<PagingData<User>>, CoroutineScope>(contextProvider) {
    override suspend fun run(params: CoroutineScope?): Either<Flow<PagingData<User>>, PicPayError> =
        when (params) {
            null -> Either.Failure(PicPayError.GenericError(Exception("Invalid Coroutine Scope")))
            else -> repository.getUserPaginated(params)
        }
}