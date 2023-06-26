package com.example.expensetrackerv2.di

import android.content.Context
import com.github.pploszczyca.expensetrackerv2.database.di.DatabaseDI
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoriesModule {
    @Provides
    fun provideCategoryRepository(@ApplicationContext appContext: Context): CategoryRepository =
        DatabaseDI.categoryRepository(appContext = appContext)

    @Provides
    fun provideExpenseRepository(@ApplicationContext appContext: Context): ExpenseRepository =
        DatabaseDI.expenseRepository(appContext = appContext)
}