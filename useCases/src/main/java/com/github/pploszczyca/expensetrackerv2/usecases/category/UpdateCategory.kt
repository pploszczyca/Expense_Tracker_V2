package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository

class UpdateCategory(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) {
        repository.update(category)
    }
}