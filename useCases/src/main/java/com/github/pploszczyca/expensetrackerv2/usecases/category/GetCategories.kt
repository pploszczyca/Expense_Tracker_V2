package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetCategories(
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