package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class DeleteAllExpensesWithItsType @Inject constructor(private val repository: ExpenseWithCategoryRepository) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}