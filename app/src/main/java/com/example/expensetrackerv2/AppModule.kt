package com.example.expensetrackerv2

import android.content.Context
import androidx.room.Room
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeDatabaseRepository
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseDatabaseRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
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
    fun provideExpenseWithItsTypeRepository(expenseDao: ExpenseDao): ExpenseWithItsTypeRepository =
        ExpenseWithItsTypeDatabaseRepository(expenseDao)

    @Provides
    @Singleton
    fun provideTypeOfExpenseRepository(expenseDao: ExpenseDao): TypeOfExpenseRepository =
        TypeOfExpenseDatabaseRepository(expenseDao)


}