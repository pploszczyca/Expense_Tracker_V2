package com.example.expensetrackerv2.ui.statistics

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.extensions.toFormattedString
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense.GetAllExpenses
import com.example.expensetrackerv2.use_cases.expense_with_category.GetAllExpenseWithCategory
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpensesStatisticsViewModel @Inject constructor(getAllExpenses: GetAllExpenses) :
    ViewModel() {
    private val _fromDate = mutableStateOf(Date().toFormattedString())
    val fromDate: State<String> = _fromDate

    private val _toDate = mutableStateOf(Date().toFormattedString())
    val toDate: State<String> = _toDate

    val expensesWithItsType: Flow<List<Expense>> =
        getAllExpenses()

    private fun changeDate(mutableStringDate: MutableState<String>, localDate: LocalDate) {
        mutableStringDate.value = localDate.toString()
    }

    fun onFromDateChange(localDate: LocalDate) {
        changeDate(_fromDate, localDate)
    }

    fun onToDateChange(localDate: LocalDate) {
        changeDate(_toDate, localDate)
    }

}