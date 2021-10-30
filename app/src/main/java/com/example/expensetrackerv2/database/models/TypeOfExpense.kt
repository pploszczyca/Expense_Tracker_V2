package com.example.expensetrackerv2.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeOfExpense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val type: Type = Type.OUTGO
)