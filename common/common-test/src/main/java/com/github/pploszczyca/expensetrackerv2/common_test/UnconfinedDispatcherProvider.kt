package com.github.pploszczyca.expensetrackerv2.common_test

import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object UnconfinedDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val default: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}