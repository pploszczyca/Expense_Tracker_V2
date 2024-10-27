package com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines

import kotlinx.coroutines.Dispatchers

internal class DispatcherProviderImpl : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}

fun DispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()