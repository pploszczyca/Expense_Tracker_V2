package com.github.pploszczyca.expensetrackerv2.common_test

import io.mockk.mockk

inline fun <reified T : Any> dummy() = mockk<T>()

inline fun <reified T : Any> noOp() = mockk<T>()