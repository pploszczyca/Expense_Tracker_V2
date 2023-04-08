package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.repositories.CategoryRepository
import javax.inject.Inject

class InsertCategory @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(categoryEntity: CategoryEntity) {
        repository.insertCategory(categoryEntity)
    }
}