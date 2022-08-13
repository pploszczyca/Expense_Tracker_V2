package com.example.expensetrackerv2.ui.main.features.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val mapper: ExpensesListGroupedExpensesMapper,
) : ViewModel() {
    var viewState by mutableStateOf(ViewState())
        private set

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.ExpensesChanged -> viewState = viewState.copy(
                groupedExpensesList = mapper.map(event.expenses)
            )
        }
    }

    data class ViewState(
        val groupedExpensesList: List<GroupedExpenses> = emptyList()
    ) {
        data class GroupedExpenses(
            val key: ExpenseMonthYearKey,
            val dateText: String,
            val expenses: List<ExpenseWithItsType>,
            val totalIncome: String,
            val totalOutgo: String,
        )
    }
}
