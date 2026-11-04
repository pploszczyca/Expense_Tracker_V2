package com.github.pploszczyca.expensetrackerv2.expense_statistics.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetAllExpenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class DateRangePreset {
    THIS_MONTH,
    LAST_7_DAYS,
    THIS_YEAR,
    ALL_TIME,
    CUSTOM,
}

@HiltViewModel
class ExpensesStatisticsViewModel @Inject constructor(
    getAllExpenses: GetAllExpenses,
    private val navigationRouter: NavigationRouter,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _fromLocalDate = mutableStateOf(LocalDate.now().withDayOfMonth(1))
    val fromLocalDate: State<LocalDate> = _fromLocalDate

    private val _toLocalDate = mutableStateOf(LocalDate.now())
    val toLocalDate: State<LocalDate> = _toLocalDate

    private val _fromDate = mutableStateOf(ExpenseDate.of(_fromLocalDate.value).toString())
    val fromDate: State<String> = _fromDate

    private val _toDate = mutableStateOf(ExpenseDate.of(_toLocalDate.value).toString())
    val toDate: State<String> = _toDate

    private val _selectedPreset = mutableStateOf(DateRangePreset.THIS_MONTH)
    val selectedPreset: State<DateRangePreset> = _selectedPreset

    val expensesWithItsType: Flow<List<Expense>> =
        getAllExpenses()

    fun onPresetSelected(preset: DateRangePreset) {
        val today = LocalDate.now()
        _selectedPreset.value = preset
        when (preset) {
            DateRangePreset.THIS_MONTH -> {
                updateDates(today.withDayOfMonth(1), today)
            }
            DateRangePreset.LAST_7_DAYS -> {
                updateDates(today.minusDays(6), today)
            }
            DateRangePreset.THIS_YEAR -> {
                updateDates(today.withDayOfYear(1), today)
            }
            DateRangePreset.ALL_TIME, DateRangePreset.CUSTOM -> Unit
        }
    }

    private fun updateDates(from: LocalDate, to: LocalDate) {
        _fromLocalDate.value = from
        _toLocalDate.value = to
        _fromDate.value = ExpenseDate.of(from).toString()
        _toDate.value = ExpenseDate.of(to).toString()
    }

    fun onFromDateChange(localDate: LocalDate) {
        _selectedPreset.value = DateRangePreset.CUSTOM
        _fromLocalDate.value = localDate
        _fromDate.value = ExpenseDate.of(localDate).toString()
    }

    fun onToDateChange(localDate: LocalDate) {
        _selectedPreset.value = DateRangePreset.CUSTOM
        _toLocalDate.value = localDate
        _toDate.value = ExpenseDate.of(localDate).toString()
    }

    fun onBackButtonClicked() {
        viewModelScope.launch(dispatcherProvider.default) {
            navigationRouter.goBack()
        }
    }
}