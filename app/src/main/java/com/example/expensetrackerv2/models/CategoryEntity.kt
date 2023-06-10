package com.example.expensetrackerv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val categoryType: CategoryType = CategoryType.OUTGO,
)