package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.repositories.CategoryRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class GetCategoriesTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    val repository: CategoryRepository = mockk()

    fun tested(
        repository: CategoryRepository
    ): GetCategories = GetCategories(
        repository = repository,
    )

    Given("Non empty categories") {
        val categories = listOf(
            CategoryEntity(
                id = 1,
                name = "My income",
                categoryType = CategoryType.INCOME
            ),
            CategoryEntity(
                id = 2,
                name = "My outgo",
                categoryType = CategoryType.OUTGO
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
                val baseIncomeCategory = CategoryEntity(
                    name = "INCOME",
                    categoryType = CategoryType.INCOME
                )
                val baseOutgoCategory = CategoryEntity(
                    name = "OUTGO",
                    categoryType = CategoryType.OUTGO
                )

                coVerify {
                    repository.insert(baseIncomeCategory)
                    repository.insert(baseOutgoCategory)
                }
            }
        }
    }
})
