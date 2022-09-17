package com.example.expensetrackerv2.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import java.util.Date

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = ExpenseConstants.NEW_EXPENSE_ID,
    val title: String = "",
    val price: Double = 0.0,
    val date: Date = Date(),
    val description: String = "",
    val place: String = "",
    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0
)

fun Expense.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)