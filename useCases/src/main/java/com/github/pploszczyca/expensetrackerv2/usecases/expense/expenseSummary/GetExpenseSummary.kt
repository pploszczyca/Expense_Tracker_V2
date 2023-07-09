package com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary

import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper.ExpenseSummaryMapper
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenseSummary(
    private val repository: ExpenseRepository,
    private val expenseSummaryMapper: ExpenseSummaryMapper,
) {
    operator fun invoke(): Flow<ExpenseSummary> =
        repository
            .getAll()
            .map(expenseSummaryMapper::toExpenseSummary)

}
