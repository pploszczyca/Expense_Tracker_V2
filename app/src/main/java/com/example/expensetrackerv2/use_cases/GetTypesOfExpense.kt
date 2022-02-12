package com.example.expensetrackerv2.use_cases

import androidx.lifecycle.LiveData
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import javax.inject.Inject

class GetTypesOfExpense @Inject constructor(private val typeOfExpenseRepository: TypeOfExpenseRepository) {
    operator fun invoke(): LiveData<List<TypeOfExpense>> =
        typeOfExpenseRepository.getAllTypeOfExpenses()
}