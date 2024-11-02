package com.github.pploszczyca.expensetrackerv2.use_cases.category

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
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
                id = dummy(),
                name = "My income",
            ),
            Category(
                id = dummy(),
                name = "My outgo",
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
})
