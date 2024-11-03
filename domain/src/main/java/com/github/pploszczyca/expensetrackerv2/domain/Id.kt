package com.github.pploszczyca.expensetrackerv2.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Id(private val value: Uuid) {

    override fun toString(): String = value.toString()

    companion object {
        fun new(): Id = Id(Uuid.random())

        fun from(value: String): Id = Id(Uuid.parse(value))

        val NO_ID = from("00000000-0000-0000-0000-000000000000")
    }
}