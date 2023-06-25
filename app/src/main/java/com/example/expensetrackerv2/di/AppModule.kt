package com.example.expensetrackerv2.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.repositories.*
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    fun provideTypeOfExpenseRepository(expenseDao: ExpenseDao): CategoryRepository =
        CategoryDatabaseRepository(expenseDao)

    @Provides
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpenseRepository =
        ExpenseDatabaseRepository(expenseDao)

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext appContext: Context): ContentResolver =
        appContext.contentResolver

    @Provides
    fun ioDispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}