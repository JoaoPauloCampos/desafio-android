package com.picpay.desafio.android.domain.core
import com.picpay.desafio.android.domain.core.Either.Failure
import com.picpay.desafio.android.domain.core.Either.Success

sealed class Either<out S, out F> {

    data class Failure<out F>(val value: F) : Either<Nothing, F>()

    data class Success<out S>(val value: S) : Either<S, Nothing>()

    val isSuccess get() = this is Success<S>
    val isFailure get() = this is Failure<F>

    fun <L> failure(a: L) = Failure(a)
    fun <R> success(b: R) = Success(b)

    fun either(fnR: (S) -> Any, fnL: (F) -> Any): Any =
        when (this) {
            is Failure -> fnL(value)
            is Success -> fnR(value)
        }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, F, S> Either<S, F>.flatMap(fn: (S) -> Either<T, F>): Either<T, F> =
    when (this) {
        is Failure -> Failure(value)
        is Success -> fn(value)
    }

fun <T, S, F> Either<S, F>.map(fn: (S) -> (T)): Either<T, F> = this.flatMap(fn.c(::success))