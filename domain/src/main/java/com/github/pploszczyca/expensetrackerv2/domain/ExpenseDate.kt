package com.github.pploszczyca.expensetrackerv2.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@JvmInline
value class ExpenseDate private constructor(private val date: LocalDate) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun of(dateString: String): ExpenseDate {
            val parsedDate = LocalDate.parse(dateString, formatter)
            return ExpenseDate(parsedDate)
        }

        fun of(date: LocalDate): ExpenseDate = ExpenseDate(date)
    }

    override fun toString(): String = date.format(formatter)

    fun isBefore(other: ExpenseDate): Boolean = date.isBefore(other.date)
    fun isAfter(other: ExpenseDate): Boolean = date.isAfter(other.date)
    fun isEqual(other: ExpenseDate): Boolean = date.isEqual(other.date)
}
