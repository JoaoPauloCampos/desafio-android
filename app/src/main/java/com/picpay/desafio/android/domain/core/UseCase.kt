package com.picpay.desafio.android.domain.core

import com.picpay.desafio.android.data.remote.core.PicPayError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out T, in Params>(
    private val contextProvider: CoroutineContextProvider
    ) {

    abstract suspend fun run(params: Params? = null): Either<T, PicPayError>

    operator fun invoke(
        scope: CoroutineScope,
        params: Params? = null,
        onError: ((PicPayError) -> Unit) = {},
        onSuccess: (T) -> Unit
    ) {
        scope.launch(contextProvider.io) {
            run(params).run {
                withContext(contextProvider.main) {
                    either(
                        {
                            onSuccess(it)
                        },
                        {
                            onError(it)
                        }
                    )
                }
            }
        }
    }
}
