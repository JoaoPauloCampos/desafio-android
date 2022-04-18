package com.picpay.desafio.android.domain.core

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
}