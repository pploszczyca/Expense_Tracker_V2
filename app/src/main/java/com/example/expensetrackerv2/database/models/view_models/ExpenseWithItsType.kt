package com.example.expensetrackerv2.database.models.view_models

import androidx.room.DatabaseView
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.Type
import java.util.*

@DatabaseView("SELECT e.id, e.title, e.price, e.date, e.description, e.place, t.id AS typeID, t.name AS typeName, t.type FROM Expense AS e INNER JOIN TypeOfExpense AS t ON e.type_of_expense_id = t.id")
data class ExpenseWithItsType(
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

fun ExpenseWithItsType.toExpense(): Expense = Expense(
    id = id,
    title = title,
    price = price,
    date = date,
    description = description,
    place = place,
    typeOfExpenseId = typeID
)

data class ExpenseMonthYearKey(val year: Int, val month: Int)

fun ExpenseWithItsType.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)


