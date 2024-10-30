package com.github.pploszczyca.expensetrackerv2.database.repositories.mappers

import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate

internal class ExpenseMapper {
    fun toDomainModel(
        expenseWithCategory: ExpenseWithCategory,
    ): Expense =
        Expense(
            id = expenseWithCategory.id.let(Id::from),
            title = expenseWithCategory.title,
            price = expenseWithCategory.price.let(Price::of),
            date = expenseWithCategory.date.let(ExpenseDate::of),
            description = expenseWithCategory.description,
            place = expenseWithCategory.place,
            type = when(expenseWithCategory.type) {
                ExpenseEntity.Type.INCOME -> Expense.Type.Income
                ExpenseEntity.Type.OUTGO -> Expense.Type.Outgo
            },
            category = getCategory(expenseWithCategory = expenseWithCategory),
        )

    private fun getCategory(
        expenseWithCategory: ExpenseWithCategory,
    ): Category? {
        val categoryId = expenseWithCategory.categoryId ?: return null
        val categoryName = expenseWithCategory.categoryName ?: return null

        return Category(
            id = Id.from(categoryId),
            name = categoryName,
        )
    }

    fun toDatabaseModel(
        expense: Expense,
    ): ExpenseEntity =
        ExpenseEntity(
            id = expense.id.toString(),
            title = expense.title,
            price = expense.price.amount,
            date = expense.date.date,
            description = expense.description,
            place = expense.place,
            type = when(expense.type) {
                Expense.Type.Income -> ExpenseEntity.Type.INCOME
                Expense.Type.Outgo -> ExpenseEntity.Type.OUTGO
            },
            categoryId = expense.category?.id?.toString(),
        )
}