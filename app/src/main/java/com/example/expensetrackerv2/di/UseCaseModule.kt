package com.example.expensetrackerv2.di

import com.github.pploszczyca.expensetrackerv2.usecases.category.DeleteCategory
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.category.InsertCategory
import com.github.pploszczyca.expensetrackerv2.usecases.category.UpdateCategory
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetAllExpenses
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesPlaces
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesTitles
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun provideDeleteCategory(repository: CategoryRepository): DeleteCategory =
        DeleteCategory(repository = repository)

    @Provides
    fun provideGetCategories(repository: CategoryRepository): GetCategories =
        GetCategories(repository = repository)

    @Provides
    fun provideInsertCategory(repository: CategoryRepository): InsertCategory =
        InsertCategory(repository = repository)

    @Provides
    fun provideUpdateCategory(repository: CategoryRepository): UpdateCategory =
        UpdateCategory(repository = repository)

    @Provides
    fun provideDeleteExpense(repository: ExpenseRepository): DeleteExpense =
        DeleteExpense(repository = repository)

    @Provides
    fun provideGetAllExpenses(repository: ExpenseRepository): GetAllExpenses =
        GetAllExpenses(repository = repository)

    @Provides
    fun provideGetExpense(repository: ExpenseRepository): GetExpense =
        GetExpense(repository = repository)

    @Provides
    fun provideGetExpensesPlaces(repository: ExpenseRepository): GetExpensesPlaces =
        GetExpensesPlaces(repository = repository)

    @Provides
    fun provideGetExpensesTitles(repository: ExpenseRepository): GetExpensesTitles =
        GetExpensesTitles(repository = repository)

    @Provides
    fun provideInsertExpense(repository: ExpenseRepository): InsertExpense =
        InsertExpense(repository = repository)

    @Provides
    fun provideUpdateExpense(repository: ExpenseRepository): UpdateExpense =
        UpdateExpense(repository = repository)
}