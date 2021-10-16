package com.example.expensetrackerv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense

@Database(entities = [Expense::class, TypeOfExpense::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase {
            synchronized(this) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context!!,
                        AppDatabase::class.java, "expense-tracker-db"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }
                return INSTANCE!!
            }
        }
    }
}