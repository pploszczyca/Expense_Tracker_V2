package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.database.models.Category
import com.example.expensetrackerv2.database.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke(): Flow<List<Category>> =
        categoryRepository.getAllCategories()
}