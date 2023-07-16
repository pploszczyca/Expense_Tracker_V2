package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.totalIncome
import com.github.pploszczyca.expensetrackerv2.domain.totalOutgo
import com.github.pploszczyca.expensetrackerv2.features.main.MainViewModel
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val navigationRouter: NavigationRouter,
    private val viewStateMapper: ExpenseListViewStateMapper,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.OnInit ->
                onInit(mainViewState = event.mainViewState)

            is ExpensesListEvent.OnEditExpenseButtonClicked ->
                navigationRouter.goToExpenseForm(expenseId = event.expense.id)
        }
    }

    private fun onInit(mainViewState: Flow<MainViewModel.ViewState>) {
        observeSearchedTitleChanged(mainViewState = mainViewState)
        observeExpenseSummaryChange(mainViewState = mainViewState)
    }

    private fun observeSearchedTitleChanged(mainViewState: Flow<MainViewModel.ViewState>) {
        viewModelScope.launch(Dispatchers.Default) {
            mainViewState
                .map { it.searchedTitle }
                .distinctUntilChanged()
                .collect { searchedTitle ->
                    _viewState.updateTransform {
                        copy(searchedTitle = searchedTitle)
                    }
                }
        }
    }

    private fun observeExpenseSummaryChange(mainViewState: Flow<MainViewModel.ViewState>) {
        viewModelScope.launch(Dispatchers.Default) {
            mainViewState
                .map { it.expenseSummary }
                .filterNotNull()
                .distinctUntilChanged()
                .collect { expenseSummary ->
                    _viewState.updateTransform {
                        copy(dailyExpenses = viewStateMapper.toDailyExpenses(expenseSummary = expenseSummary))
                    }
                }
        }
    }

    data class ViewState(
        private val dailyExpenses: List<DailyExpense> = emptyList(),
        private val searchedTitle: String = "",
    ) {
        val filteredDailyExpenses: List<DailyExpense>
            get() =
                dailyExpenses.map { dailyExpense ->
                    val filteredExpenses = dailyExpense.expenses.filter { expense ->
                        expense.title.contains(
                            searchedTitle,
                            true
                        )
                    }

                    dailyExpense.copy(
                        expenses = filteredExpenses,
                        totalIncome = filteredExpenses.totalIncome.toString(),
                        totalOutgo = filteredExpenses.totalOutgo.toString(),
                    )
                }.filter { it.expenses.isNotEmpty() }

        data class DailyExpense(
            val date: String,
            val expenses: List<Expense>,
            val totalIncome: String,
            val totalOutgo: String,
        )
    }
}
