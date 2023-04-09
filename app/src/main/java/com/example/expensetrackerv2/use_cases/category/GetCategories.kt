package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<CategoryEntity>> =
        repository
            .getAll()
            .onEach { categories ->
                if (categories.isEmpty()) {
                    addBaseCategories()
                }
            }

    private suspend fun addBaseCategories() {
        val baseIncomeCategory = CategoryEntity(
            name = "INCOME",
            categoryType = CategoryType.INCOME
        )
        val baseOutgoCategory = CategoryEntity(
            name = "OUTGO",
            categoryType = CategoryType.OUTGO
        )

        repository.insert(baseIncomeCategory, baseOutgoCategory)
    }
}