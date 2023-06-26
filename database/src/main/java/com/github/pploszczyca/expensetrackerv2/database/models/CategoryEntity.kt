package com.github.pploszczyca.expensetrackerv2.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
internal data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val categoryType: CategoryType = CategoryType.OUTGO,
)