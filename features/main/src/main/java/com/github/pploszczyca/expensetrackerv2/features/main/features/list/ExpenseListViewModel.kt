package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.GetExpenseSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val navigationRouter: NavigationRouter,
    private val getExpenseSummary: GetExpenseSummary,
    private val viewStateMapper: ExpenseListViewStateMapper,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getExpenseSummary().collect { expenseSummary ->
                _viewState.updateTransform {
                    copy(dailyExpenses = viewStateMapper.toDailyExpenses(expenseSummary = expenseSummary))
                }
            }
        }
    }

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.ExpensesChanged -> Unit
//                _viewState.updateTransform {
//                    copy(
//                        groupedExpensesList = mapper.map(event.expenses)
//                    )
//                }

            is ExpensesListEvent.OnEditExpenseButtonClicked ->
                navigationRouter.goToExpenseForm(expenseId = event.expense.id)
        }
    }

    data class ViewState(
        val dailyExpenses: List<DailyExpense> = emptyList(),
    ) {
        data class DailyExpense(
            val date: String,
            val expenses: List<Expense>,
            val totalIncome: String,
            val totalOutgo: String,
        )
    }
}
