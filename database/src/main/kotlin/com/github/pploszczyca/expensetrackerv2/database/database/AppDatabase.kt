package com.github.pploszczyca.expensetrackerv2.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.pploszczyca.expensetrackerv2.database.models.CategoryEntity
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import com.github.pploszczyca.expensetrackerv2.database.models.WalletEntity
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseWithCategory

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, WalletEntity::class],
    views = [ExpenseWithCategory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}