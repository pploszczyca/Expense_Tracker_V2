package com.github.pploszczyca.expensetrackerv2.use_cases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

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

        coEvery { repository.get(expenseId) } returns expense

        When("Get expense is invoked") {
            val actual = tested(repository).invoke(expenseId = expenseId)

            Then("Expense is returned") {
                actual shouldBe expense
                coVerify { repository.get(expenseId = expenseId) }
            }
        }
    }
})
