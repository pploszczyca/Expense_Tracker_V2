package com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.updateTransform(transform: T.() -> T) {
    value = value.transform()
}