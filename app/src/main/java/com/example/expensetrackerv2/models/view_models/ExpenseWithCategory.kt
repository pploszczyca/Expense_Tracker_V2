package com.example.expensetrackerv2.models.view_models

import androidx.room.DatabaseView
import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.models.ExpenseEntity
import java.util.*

@DatabaseView("SELECT e.id, e.title, e.price, e.date, e.description, e.place, c.id AS categoryId, c.name AS categoryName, c.categoryType FROM Expense AS e INNER JOIN Category AS c ON e.category_id = c.id")
data class ExpenseWithCategory(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,        // price >= 0
    val date: Date = Date(),
    val description: String = "",
    val place: String = "",
    val categoryId: Int = 0,
    val categoryName: String = "",
    val categoryType: CategoryType = CategoryType.OUTGO,
)

fun ExpenseWithCategory.toExpense(): ExpenseEntity = ExpenseEntity(
    id = id,
    title = title,
    price = price,
    date = date,
    description = description,
    place = place,
    categoryId = categoryId
)

data class ExpenseMonthYearKey(val year: Int, val month: Int)

fun ExpenseWithCategory.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)


