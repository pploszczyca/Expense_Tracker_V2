package com.github.pploszczyca.expensetrackerv2.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "expense")
internal data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val price: BigDecimal,
    val date: LocalDate,
    val description: String?,
    val place: String?,
    val type: Type,
    @ColumnInfo(name = "category_id")
    val categoryId: String?,
) {
    sealed interface Type {
        data object Income : Type
        data object Outgo : Type
    }
}