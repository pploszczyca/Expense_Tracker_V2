package com.example.expensetrackerv2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.WalletEntity
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, WalletEntity::class],
    views = [ExpenseWithCategory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}