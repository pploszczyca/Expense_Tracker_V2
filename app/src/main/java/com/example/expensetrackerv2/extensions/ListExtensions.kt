package com.example.expensetrackerv2.extensions

inline fun <T> Iterable<T>.filterIf(
    condition: Boolean,
    predicate: (T) -> Boolean,
): List<T> =
    when(condition) {
        true -> this.filter(predicate)
        false -> this.toList()
    }