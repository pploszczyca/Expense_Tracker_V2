package com.example.expensetrackerv2.use_cases.category

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<Category>> =
        repository
            .getAll()
            .onEach { categories ->
                if (categories.isEmpty()) {
                    addBaseCategories()
                }
            }

    private suspend fun addBaseCategories() {
        val baseIncomeCategory = Category(
            name = "INCOME",
            type = Category.Type.INCOME
        )
        val baseOutgoCategory = Category(
            name = "OUTGO",
            type = Category.Type.OUTGO
        )

        repository.insert(baseIncomeCategory)
        repository.insert(baseOutgoCategory)
    }
}