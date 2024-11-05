package com.github.pploszczyca.expensetrackerv2.database.repositories

import com.github.pploszczyca.expensetrackerv2.database.database.ExpenseDao
import com.github.pploszczyca.expensetrackerv2.database.repositories.mappers.ExpenseMapper
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

internal class ExpenseDatabaseRepository(
    private val dao: ExpenseDao,
    private val expenseMapper: ExpenseMapper = ExpenseMapper(),
) : ExpenseRepository {
    override fun observe(): Flow<List<Expense>> =
        dao.observeExpenseWithCategory().mapNotNull { it.map(expenseMapper::toDomainModel) }

    override suspend fun getAll(): List<Expense> =
        dao.getExpensesWithCategory().map(expenseMapper::toDomainModel)

    override suspend fun get(expenseId: Id): Expense =
        dao.getExpenseWithCategory(expenseID = expenseId.toString())
            .let(expenseMapper::toDomainModel)

    override suspend fun insert(expense: Expense) {
        dao.insertAllExpenses(expense.let(expenseMapper::toDatabaseModel))
    }

    override suspend fun update(expense: Expense) {
        dao.updateExpense(expense.let(expenseMapper::toDatabaseModel))
    }

    override suspend fun delete(expense: Expense) {
        dao.deleteExpense(expense.let(expenseMapper::toDatabaseModel))
    }
}