package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategory @Inject constructor(
    private val repository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<CategoryEntity>> =
        repository.getAllCategories()
}