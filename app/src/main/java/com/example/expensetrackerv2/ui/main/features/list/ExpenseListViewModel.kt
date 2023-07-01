package com.example.expensetrackerv2.ui.main.features.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val mapper: ExpensesListGroupedExpensesMapper,
    private val navigationRouter: NavigationRouter,
) : ViewModel() {
    var viewState by mutableStateOf(ViewState())
        private set

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.ExpensesChanged -> viewState = viewState.copy(
                groupedExpensesList = mapper.map(event.expenses)
            )

            is ExpensesListEvent.OnEditExpenseButtonClicked ->
                navigationRouter.goToExpenseForm(expenseId = event.expense.id)
        }
    }

    data class ViewState(
        val groupedExpensesList: List<GroupedExpenses> = emptyList(),
    ) {
        data class GroupedExpenses(
            val key: Expense.MonthYearKey,
            val dateText: String,
            val expenses: List<Expense>,
            val totalIncome: String,
            val totalOutgo: String,
        )
    }
}
