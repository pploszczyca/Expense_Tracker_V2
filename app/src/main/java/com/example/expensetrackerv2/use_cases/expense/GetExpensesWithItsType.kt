package com.example.expensetrackerv2.use_cases.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import javax.inject.Inject

class GetExpensesWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    operator fun invoke(): LiveData<List<ExpenseWithItsType>> =
        expenseWithItsTypeRepository.getExpenses()

    operator fun invoke(
        expenseMonthYearKey: ExpenseMonthYearKey?,
        titleToSearch: String
    ): LiveData<List<ExpenseWithItsType>> =
        Transformations.map(expenseWithItsTypeRepository.getExpenses()) { it ->
            it.filter {
                (expenseMonthYearKey == null || it.getKey() == expenseMonthYearKey) && it.title.contains(
                    titleToSearch, true
                )
            }
        }
}