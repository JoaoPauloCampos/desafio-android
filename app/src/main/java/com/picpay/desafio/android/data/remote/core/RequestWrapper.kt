package com.picpay.desafio.android.data.remote.core

import com.picpay.desafio.android.domain.core.Either
import retrofit2.HttpException

suspend fun <D> requestWrapper(
    call: suspend () -> D
): Either<D, PicPayError> {
    return try {
        val dataFromRemote = call()
        Either.Success(dataFromRemote)
    } catch (ex: HttpException) {
        val picPayError = if (ex.code() == 404) {
            PicPayError.UserNotFound
        } else PicPayError.GenericError(ex)

        return Either.Failure(picPayError)
    }
    catch (ex: Exception) {
        return Either.Failure(PicPayError.GenericError(ex))
    }
}

sealed class PicPayError {
    object UserNotFound: PicPayError()
    data class GenericError(val exception: Exception): PicPayError()
}