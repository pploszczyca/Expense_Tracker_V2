package com.example.expensetrackerv2.providers

import com.example.expensetrackerv2.models.Expense

object SampleDataProvider {
    fun sampleExpenses(): List<Expense> {
        return listOf(
            Expense(id = 0, title = "Zakupy w Biedronce", price = -50.0, description = "Opis", place = "Wadowice"),
            Expense(id = 1, title = "Zakupy w Lidlu", price = -30.0, description = "Opis", place = "Wadowice"),
            Expense(id = 2, title = "Kabel HDMI", price = -100.0, description = "Opis", place = "Kraków"),
            Expense(id = 3, title = "Na życie", price = 1000.0, description = "Opis", place = "")
        )
    }
}