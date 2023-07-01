package com.github.pploszczyca.expensetrackerv2.expense_statistics.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toFormattedString
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetAllExpenses
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpensesStatisticsViewModel @Inject constructor(
    getAllExpenses: GetAllExpenses,
    private val navigationRouter: NavigationRouter,
) :
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

    fun onBackButtonClicked() {
        navigationRouter.goBack()
    }
}