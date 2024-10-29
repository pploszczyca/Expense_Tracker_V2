package com.github.pploszczyca.expensetrackerv2.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@JvmInline
value class Id private constructor(private val value: Uuid) {

    override fun toString(): String = value.toString()

    companion object {
        fun new(): Id = Id(Uuid.random())

        fun fromString(value: String): Id = Id(Uuid.parse(value))
    }
}