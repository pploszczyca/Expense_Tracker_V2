package com.github.pploszczyca.expensetrackerv2.database.repositories

import com.github.pploszczyca.expensetrackerv2.database.database.ExpenseDao
import com.github.pploszczyca.expensetrackerv2.database.repositories.mappers.ExpenseMapper
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ExpenseDatabaseRepository(
    private val dao: ExpenseDao,
    private val expenseMapper: ExpenseMapper = ExpenseMapper(),
) : ExpenseRepository {
    override fun getAll(): Flow<List<Expense>> =
        dao.getAllExpenseWithCategory().map { it.map(expenseMapper::toDomainModel) }

    override fun get(expenseId: Int): Flow<Expense> =
        dao.getExpenseWithItsType(expenseID = expenseId).map(expenseMapper::toDomainModel)

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