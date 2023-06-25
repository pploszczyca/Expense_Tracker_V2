package com.example.expensetrackerv2.use_cases.category

import com.example.expensetrackerv2.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import javax.inject.Inject

class UpdateCategory @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) {
        repository.update(category)
    }
}