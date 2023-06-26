package com.github.pploszczyca.expensetrackerv2.database.di

import android.content.Context
import androidx.room.Room
import com.github.pploszczyca.expensetrackerv2.database.database.AppDatabase

object DatabaseDI {
    private const val DATABASE_NAME = "expense-tracker-db"

    private lateinit var appDatabaseField: AppDatabase

    fun appDatabase(appContext: Context): AppDatabase {
        if(::appDatabaseField.isInitialized.not()) {
            appDatabaseField = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }

        return appDatabaseField
    }


}