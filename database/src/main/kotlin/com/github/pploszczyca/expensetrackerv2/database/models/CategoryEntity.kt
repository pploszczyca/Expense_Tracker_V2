package com.github.pploszczyca.expensetrackerv2.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
internal data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
)