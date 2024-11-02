package com.github.pploszczyca.expensetrackerv2.use_cases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetExpenseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: ExpenseRepository = mockk()

    fun tested(
        repository: ExpenseRepository = mockk(),
    ): GetExpense = GetExpense(
        repository = repository,
    )

    Given("Expense Id") {
        val expenseId = Id.new()
        val expense: Expense = mockk()
        val expenseFlow: Flow<Expense> = flowOf(expense)

        every { repository.get(eq(expenseId)) } returns expenseFlow

        When("Get expense is invoked") {
            val actual = tested(repository).invoke(expenseId = expenseId)

            Then("Expense is returned") {
                actual shouldBe expenseFlow
                verify { repository.get(expenseId = eq(expenseId)) }
            }
        }
    }
})
