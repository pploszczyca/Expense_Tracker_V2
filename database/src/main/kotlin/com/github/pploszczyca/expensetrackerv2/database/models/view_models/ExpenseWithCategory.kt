package com.github.pploszczyca.expensetrackerv2.database.models.view_models

import androidx.room.DatabaseView
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import java.math.BigDecimal
import java.time.LocalDate

@DatabaseView("SELECT e.id, e.title, e.price, e.date, e.description, e.place, c.id AS categoryId, c.name AS categoryName, e.type FROM Expense AS e LEFT JOIN Category AS c ON e.category_id = c.id")
internal data class ExpenseWithCategory(
    val id: String,
    val title: String,
    val price: BigDecimal,
    val date: LocalDate,
    val description: String?,
    val place: String?,
    val categoryId: String?,
    val categoryName: String?,
    val type: ExpenseEntity.Type,
)
