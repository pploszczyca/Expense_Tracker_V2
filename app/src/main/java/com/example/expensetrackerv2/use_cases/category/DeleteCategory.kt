package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.database.models.Category
import com.example.expensetrackerv2.database.repositories.CategoryRepository
import javax.inject.Inject

class DeleteCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) =
        categoryRepository.deleteCategory(category)

}