package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import androidx.lifecycle.ViewModel
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val mapper: ExpensesListGroupedExpensesMapper,
    private val navigationRouter: NavigationRouter,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.ExpensesChanged -> _viewState.updateTransform {
                copy(
                    groupedExpensesList = mapper.map(event.expenses)
                )
            }

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
