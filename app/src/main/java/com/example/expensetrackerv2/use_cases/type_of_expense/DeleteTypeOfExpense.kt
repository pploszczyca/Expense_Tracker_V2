package com.example.expensetrackerv2.use_cases.type_of_expense

import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import javax.inject.Inject

class DeleteTypeOfExpense @Inject constructor(private val typeOfExpenseRepository: TypeOfExpenseRepository) {
    suspend operator fun invoke(typeOfExpense: TypeOfExpense) =
        typeOfExpenseRepository.deleteTypeOfExpense(typeOfExpense)

}