package com.example.expensetrackerv2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Category
import com.example.expensetrackerv2.models.Wallet
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory

@Database(
    entities = [Expense::class, Category::class, Wallet::class],
    views = [ExpenseWithCategory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}