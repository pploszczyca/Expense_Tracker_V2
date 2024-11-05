package com.github.pploszczyca.expensetrackerv2.use_cases.category

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

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
        val categories: List<Category> = listOf(
            dummy(),
            dummy(),
            dummy(),
        )

        coEvery { repository.getAll() } returns categories

        When("UC is invoked") {
            val actual = tested(repository = repository).invoke()

            Then("Categories as result") {
                actual shouldBe categories
            }
        }
    }
})
