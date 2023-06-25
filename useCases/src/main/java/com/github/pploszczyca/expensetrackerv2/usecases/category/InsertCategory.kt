package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import javax.inject.Inject

class InsertCategory @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) {
        repository.insert(category)
    }
}