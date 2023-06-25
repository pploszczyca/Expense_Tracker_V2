package com.example.expensetrackerv2.repositories.mappers

import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense

internal class ExpenseMapper {
    fun toDomainModel(
        expenseWithCategory: ExpenseWithCategory,
    ): Expense =
        Expense(
            id = expenseWithCategory.id,
            title = expenseWithCategory.title,
            price = expenseWithCategory.price,
            date = expenseWithCategory.date,
            description = expenseWithCategory.description,
            place = expenseWithCategory.place,
            category = getCategory(expenseWithCategory = expenseWithCategory),
        )

    private fun getCategory(
        expenseWithCategory: ExpenseWithCategory,
    ): Category =
        Category(
            id = expenseWithCategory.categoryId,
            name = expenseWithCategory.categoryName,
            type = when (expenseWithCategory.categoryType) {
                CategoryType.INCOME -> Category.Type.INCOME
                CategoryType.OUTGO -> Category.Type.OUTGO
            }
        )

    fun toDatabaseModel(
        expense: Expense,
    ): ExpenseEntity =
        ExpenseEntity(
            id = expense.id,
            title = expense.title,
            price = expense.price,
            date = expense.date,
            description = expense.description,
            place = expense.place,
            categoryId = expense.category.id,
        )
}