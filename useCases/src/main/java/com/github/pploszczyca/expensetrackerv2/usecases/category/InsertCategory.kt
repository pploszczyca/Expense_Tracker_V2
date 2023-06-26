package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository

class InsertCategory(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) {
        repository.insert(category)
    }
}