package com.github.pploszczyca.expensetrackerv2.database.models.view_models

import androidx.room.DatabaseView
import com.github.pploszczyca.expensetrackerv2.database.models.CategoryType
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import java.util.*

@DatabaseView("SELECT e.id, e.title, e.price, e.date, e.description, e.place, c.id AS categoryId, c.name AS categoryName, c.categoryType FROM Expense AS e INNER JOIN Category AS c ON e.category_id = c.id")
internal data class ExpenseWithCategory(
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
