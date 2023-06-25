package com.example.expensetrackerv2.use_cases.category

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class GetCategoriesTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: CategoryRepository = mockk()

    fun tested(
        repository: CategoryRepository,
    ): GetCategories = GetCategories(
        repository = repository,
    )

    Given("Non empty categories") {
        val categories = listOf(
            Category(
                id = 1,
                name = "My income",
                type = Category.Type.INCOME
            ),
            Category(
                id = 2,
                name = "My outgo",
                type = Category.Type.OUTGO
            ),
        )

        every { repository.getAll() } returns flowOf(categories)

        When("UC is invoked") {
            val actual = tested(repository = repository).invoke()

            Then("Categories as result") {
                launch {
                    actual.first() shouldBe categories
                }
            }
        }
    }

    Given("Empty categories") {
        every { repository.getAll() } returns flowOf(emptyList())
        coEvery { repository.insert(any()) } returns Unit

        When("UC is invoked") {
            tested(repository = repository)
                .invoke()
                .first()

            Then("Insert base categories") {
                val baseIncomeCategory = Category(
                    name = "INCOME",
                    type = Category.Type.INCOME
                )
                val baseOutgoCategory = Category(
                    name = "OUTGO",
                    type = Category.Type.OUTGO
                )

                coVerify {
                    repository.insert(baseIncomeCategory)
                    repository.insert(baseOutgoCategory)
                }
            }
        }
    }
})
