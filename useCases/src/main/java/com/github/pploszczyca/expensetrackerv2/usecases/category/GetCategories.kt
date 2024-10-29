package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetCategories(
    private val repository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<Category>> =
        repository.getAll()
}