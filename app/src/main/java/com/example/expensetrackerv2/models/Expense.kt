package com.example.expensetrackerv2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var price: Double = 0.0,        // price >= 0
    var date: Date = Date(),
    var description: String = "",
    var place: String = "",
    @ColumnInfo(name = "type_of_expense_id")
    var typeOfExpenseId: Int = 0
)

data class ExpenseMonthYearKey(val year: Int, val month: Int)
fun Expense.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)

