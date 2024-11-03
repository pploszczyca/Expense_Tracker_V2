package com.github.pploszczyca.expensetrackerv2.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ExpenseDate(val date: LocalDate) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        fun of(dateString: String): ExpenseDate {
            val parsedDate = LocalDate.parse(dateString, formatter)
            return ExpenseDate(parsedDate)
        }

        fun of(date: LocalDate): ExpenseDate = ExpenseDate(date)

        fun now(): ExpenseDate = ExpenseDate(LocalDate.now())
    }

    override fun toString(): String = date.format(formatter)

    fun isBefore(other: ExpenseDate): Boolean = date.isBefore(other.date)
    fun isAfter(other: ExpenseDate): Boolean = date.isAfter(other.date)
    fun isEqual(other: ExpenseDate): Boolean = date.isEqual(other.date)

    val year: Int get() = date.year
    val month: Int get() = date.monthValue
    val day: Int get() = date.dayOfMonth

    val monthYear: String get() = date.format(DateTimeFormatter.ofPattern("MM-yyyy"))
}
