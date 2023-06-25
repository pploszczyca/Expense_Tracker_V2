package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import javax.inject.Inject

class DeleteCategory @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) =
        repository.delete(category)

}