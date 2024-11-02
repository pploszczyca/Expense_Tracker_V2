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
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject

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
        val price: Price = Price.of(324.0)
        val date: ExpenseDate = ExpenseDate.now()
        val description = "description"
        val place = "place"
        val type = Expense.Type.Income
        val category: Category = Category.new("fake category")
        val expense: Expense = dummy()

        mockkObject(Expense.Companion)
        every {
            Expense.new(
                title = any(),
                price = any(),
                date = any(),
                description = any(),
                place = any(),
                type = any(),
                category = any(),
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
                        title = eq(title),
                        price = eq(price),
                        date = eq(date),
                        description = eq(description),
                        place = eq(place),
                        type = eq(type),
                        category = eq(category),
                    )
                    repository.insert(expense)
                }
            }
        }
    }
})
