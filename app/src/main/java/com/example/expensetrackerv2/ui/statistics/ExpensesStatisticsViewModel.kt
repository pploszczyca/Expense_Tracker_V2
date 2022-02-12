package com.example.expensetrackerv2.ui.statistics

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.GetExpensesWithItsType
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpensesStatisticsViewModel @Inject constructor(getExpensesWithItsType: GetExpensesWithItsType) :
    ViewModel() {
    private val _fromDate = mutableStateOf(DateUtils.toOnlyDateString(Date()))
    val fromDate: State<String> = _fromDate

    private val _toDate = mutableStateOf(DateUtils.toOnlyDateString(Date()))
    val toDate: State<String> = _toDate

    val expenseWithItsTypeLiveDataList: LiveData<List<ExpenseWithItsType>> =
        getExpensesWithItsType()

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