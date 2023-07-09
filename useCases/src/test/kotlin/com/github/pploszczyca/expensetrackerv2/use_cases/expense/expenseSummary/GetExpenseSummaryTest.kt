package com.github.pploszczyca.expensetrackerv2.use_cases.expense.expenseSummary

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.common_test.noOp
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.GetExpenseSummary
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper.ExpenseSummaryMapper
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetExpenseSummaryTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: ExpenseRepository = mockk()
    val expenseSummaryMapper: ExpenseSummaryMapper = mockk()

    fun tested(
        repository: ExpenseRepository = noOp(),
        expenseSummaryMapper: ExpenseSummaryMapper = noOp(),
    ): GetExpenseSummary = GetExpenseSummary(
        repository = repository,
        expenseSummaryMapper = expenseSummaryMapper,
    )

    Given("Repository that returns expenses") {
        val expenses: List<Expense> = dummy()
        val expenseSummary: ExpenseSummary = dummy()
        val expensesFlow: Flow<List<Expense>> = flowOf(expenses)

        every { repository.getAll() } returns expensesFlow
        every { expenseSummaryMapper.toExpenseSummary(any()) } returns expenseSummary

        When("Get Expense Summary is invoked") {
            val actual = tested(
                repository = repository,
                expenseSummaryMapper = expenseSummaryMapper,
            ).invoke().first()

            Then("Expense Summary is returned") {
                actual shouldBe expenseSummary
                verifyOrder {
                    repository.getAll()
                    expenseSummaryMapper.toExpenseSummary(expenses = expenses)
                }
            }
        }
    }
})