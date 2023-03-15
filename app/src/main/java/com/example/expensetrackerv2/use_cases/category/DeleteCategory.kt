package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.repositories.CategoryRepository
import javax.inject.Inject

class DeleteCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryEntity: CategoryEntity) =
        categoryRepository.deleteCategory(categoryEntity)

}