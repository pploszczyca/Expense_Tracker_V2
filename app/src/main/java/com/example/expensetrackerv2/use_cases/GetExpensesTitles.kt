package com.example.expensetrackerv2.use_cases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject

class GetExpensesTitles @Inject constructor(private val getExpensesWithItsType: GetExpensesWithItsType) {
    operator fun invoke(): LiveData<List<String>> =
        Transformations.map(getExpensesWithItsType()) { it ->
            it.map { it.title }
        }
}