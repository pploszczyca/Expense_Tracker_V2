package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.Category
import com.example.expensetrackerv2.repositories.CategoryRepository
import javax.inject.Inject

class UpdateCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) =
        categoryRepository.updateCategory(category)
}