package com.github.pploszczyca.expensetrackerv2.use_cases.expense

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
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
        val id: Id = dummy()
        val title = "title"
        val price: Price = dummy()
        val date: ExpenseDate = dummy()
        val description = "description"
        val place = "place"
        val type: Expense.Type = dummy()
        val category: Category = dummy()

        coEvery { repository.update(any()) } returns Unit

        When("Insert expense is invoked") {
            tested(repository).invoke(
                id = id,
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                type = type,
                category = category,
            )

            Then("New expense will be inserted") {
                val expense = Expense(
                    id = id,
                    title = title,
                    price = price,
                    date = date,
                    description = description,
                    place = place,
                    type = type,
                    category = category,
                )

                coVerify { repository.update(expense) }
            }
        }
    }
})
