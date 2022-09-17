package com.example.expensetrackerv2.models.view_models

import androidx.room.DatabaseView
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Type
import com.example.expensetrackerv2.models.Category
import java.util.*

@DatabaseView("SELECT e.id, e.title, e.price, e.date, e.description, e.place, t.id AS typeID, t.name AS typeName, t.type FROM Expense AS e INNER JOIN Category AS t ON e.category_id = t.id")
data class ExpenseWithCategory(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,        // price >= 0
    val date: Date = Date(),
    val description: String = "",
    val place: String = "",
    val typeID: Int = 0,
    val typeName: String = "",
    val type: Type = Type.OUTGO
)

fun ExpenseWithCategory.toExpense(): Expense = Expense(
    id = id,
    title = title,
    price = price,
    date = date,
    description = description,
    place = place,
    categoryId = typeID
)

fun ExpenseWithCategory.getTypeOfExpense(): Category = Category(
    id = typeID,
    name = typeName,
    type = type
)

data class ExpenseMonthYearKey(val year: Int, val month: Int)

fun ExpenseWithCategory.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)


