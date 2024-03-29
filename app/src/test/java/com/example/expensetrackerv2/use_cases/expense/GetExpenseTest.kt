package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow

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
        val expenseId = 69
        val expenseFlow: Flow<ExpenseEntity> = mockk()

        every { repository.get(any()) } returns expenseFlow

        When("Get expense is invoked") {
            val actual = tested(repository).invoke(expenseId = expenseId)

            Then("Expense is returned") {
                actual shouldBe expenseFlow
                verify { repository.get(expenseId = expenseId) }
            }
        }
    }
})
