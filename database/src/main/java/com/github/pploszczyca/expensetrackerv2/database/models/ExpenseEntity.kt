package com.github.pploszczyca.expensetrackerv2.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import java.util.Date

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = ExpenseConstants.NEW_EXPENSE_ID,
    val title: String = "",
    val price: Double = 0.0,
    val date: Date = Date(),
    val description: String = "",
    val place: String = "",
    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0,
    @ColumnInfo(name = "wallet_id")
    val walletId: Int = 0,
)

fun ExpenseEntity.getKey() = ExpenseMonthYearKey(this.date.year, this.date.month)