package com.github.pploszczyca.expensetrackerv2.use_cases.expense

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.mockkObject
import java.util.Date

class InsertExpenseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: ExpenseRepository = mockk()

    fun tested(
        repository: ExpenseRepository = mockk(),
    ): InsertExpense = InsertExpense(
        repository = repository,
    )

    Given("New expense attributes") {
        val title = "title"
        val price: Price = dummy()
        val date: ExpenseDate = dummy()
        val description = "description"
        val place = "place"
        val type: Expense.Type = dummy()
        val category: Category = mockk()
        val expense: Expense = dummy()

        mockkObject(Expense.Companion)
        coEvery {
            Expense.new(
                title = any(),
                price = any(),
                date = any(),
                description = any(),
                place = any(),
                type = any(),
                category = any()
            )
        } returns expense
        coEvery { repository.insert(any()) } returns Unit

        When("Insert expense is invoked") {
            tested(repository).invoke(
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                type = type,
                category = category,
            )

            Then("New expense will be inserted") {
                coVerifyOrder {
                    Expense.new(
                        title = title,
                        price = price,
                        date = date,
                        description = description,
                        place = place,
                        type = type,
                        category = category,
                    )
                    repository.insert(expense)
                }
            }
        }
    }
})
