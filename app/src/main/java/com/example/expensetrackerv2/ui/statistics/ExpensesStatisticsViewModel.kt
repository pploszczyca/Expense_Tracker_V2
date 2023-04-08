package com.example.expensetrackerv2.ui.statistics

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense_with_category.GetAllExpenseWithCategory
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpensesStatisticsViewModel @Inject constructor(getAllExpenseWithCategory: GetAllExpenseWithCategory) :
    ViewModel() {
    private val _fromDate = mutableStateOf(DateUtils.toOnlyDateString(Date()))
    val fromDate: State<String> = _fromDate

    private val _toDate = mutableStateOf(DateUtils.toOnlyDateString(Date()))
    val toDate: State<String> = _toDate

    val expensesWithItsType: Flow<List<ExpenseWithCategory>> =
        getAllExpenseWithCategory()

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