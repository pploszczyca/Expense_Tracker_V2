package com.example.expensetrackerv2

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.repositories.ExpenseWithCategoryDatabaseRepository
import com.example.expensetrackerv2.database.repositories.ExpenseWithCategoryRepository
import com.example.expensetrackerv2.database.repositories.CategoryDatabaseRepository
import com.example.expensetrackerv2.database.repositories.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "expense-tracker-db"
        ).build()

    @Provides
    @Singleton
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao = appDatabase.expenseDao()

    @Provides
    @Singleton
    fun provideExpenseWithItsTypeRepository(expenseDao: ExpenseDao): ExpenseWithCategoryRepository =
        ExpenseWithCategoryDatabaseRepository(expenseDao)

    @Provides
    @Singleton
    fun provideTypeOfExpenseRepository(expenseDao: ExpenseDao): CategoryRepository =
        CategoryDatabaseRepository(expenseDao)

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext appContext: Context): ContentResolver =
        appContext.contentResolver
}