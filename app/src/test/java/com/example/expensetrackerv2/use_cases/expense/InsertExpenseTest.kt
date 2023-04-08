package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.Date

class InsertExpenseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: ExpenseRepository = mockk()

    fun tested(
        repository: ExpenseRepository = mockk()
    ): InsertExpense = InsertExpense(
        repository = repository,
    )

    Given("New expense attributes") {
        val title = "title"
        val price = 21.37
        val date: Date = mockk()
        val description = "description"
        val place = "place"
        val categoryId = 59

        coEvery { repository.insert(any()) } returns Unit

        When("Insert expense is invoked") {
            tested(repository).invoke(
              title = title,
              price = price,
              date = date,
              description = description,
              place = place,
              categoryId = categoryId,
            )

            Then("New expense will be inserted") {
                val expense = ExpenseEntity(
                    title = title,
                    price = price,
                    date = date,
                    description = description,
                    place = place,
                    categoryId = categoryId,
                )

                coVerify { repository.insert(expense) }
            }
        }
    }
})
