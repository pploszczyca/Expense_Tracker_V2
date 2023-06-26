package com.github.pploszczyca.expensetrackerv2.database.repositories.mappers

import com.github.pploszczyca.expensetrackerv2.database.models.CategoryType
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseWithCategory
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