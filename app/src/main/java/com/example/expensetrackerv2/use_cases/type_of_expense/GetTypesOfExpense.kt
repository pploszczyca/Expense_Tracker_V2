package com.example.expensetrackerv2.use_cases.type_of_expense

import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTypesOfExpense @Inject constructor(private val typeOfExpenseRepository: TypeOfExpenseRepository) {
    operator fun invoke(): Flow<List<TypeOfExpense>> =
        typeOfExpenseRepository.getAllTypeOfExpenses()
}