package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.Date

class UpdateExpenseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: ExpenseRepository = mockk()

    fun tested(
        repository: ExpenseRepository = mockk(),
    ): UpdateExpense = UpdateExpense(
        repository = repository,
    )

    Given("New expense attributes") {
        val id = 99
        val title = "title"
        val price = 21.37
        val date: Date = mockk()
        val description = "description"
        val place = "place"
        val categoryId = 59

        coEvery { repository.update(any()) } returns Unit

        When("Insert expense is invoked") {
            tested(repository).invoke(
                id = id,
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                categoryId = categoryId,
            )

            Then("New expense will be inserted") {
                val expense = ExpenseEntity(
                    id = id,
                    title = title,
                    price = price,
                    date = date,
                    description = description,
                    place = place,
                    categoryId = categoryId,
                )

                coVerify { repository.update(expense) }
            }
        }
    }

})
