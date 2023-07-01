package com.example.expensetrackerv2.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.ui.main.features.bottom_bar.MainBottomBarEvent
import com.example.expensetrackerv2.ui.main.features.filter_dialog.MainFilterDialogEvent
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetAllExpenses
import com.example.expensetrackerv2.utilities.MathUtils
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllExpenses: GetAllExpenses,
    private val deleteExpense: DeleteExpense,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    filterDialogChannel: Channel<MainFilterDialogEvent>,
    private val navigationRouter: NavigationRouter,
) : ViewModel() {
    var viewState by mutableStateOf(ViewState())
        private set

    var openDrawer: (() -> Unit)? = null    // TODO: Think how to change it

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getAllExpenses()
                .collect { expenses ->
                    viewState = viewState.copy(expenses = expenses)
                }
        }

        viewModelScope.launch(Dispatchers.Main) {
            bottomBarChannel.consumeEach(::onBottomBarEvent)
        }

        viewModelScope.launch(Dispatchers.Main) {
            filterDialogChannel.consumeEach(::onFilterDialogEvent)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchedTitleChange -> viewState = viewState.copy(
                searchedTitle = event.value,
            )

            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expenseToDelete?.let {
                        deleteExpense(expense = it)
                    }
                }
                viewState = viewState.copy(
                    deleteDialogVisible = false
                )
            }

            is MainEvent.DeleteButtonClick -> viewState = viewState.copy(
                expenseToDelete = event.value,
                deleteDialogVisible = true,
            )

            is MainEvent.DismissDeleteButtonClick -> viewState = viewState.copy(
                deleteDialogVisible = false
            )

            is MainEvent.OnTopBarTrailingIconClick -> viewState = viewState.copy(
                topBarVisible = false,
                searchedTitle = "",
            )

            MainEvent.OnAddNewExpenseButtonClicked -> navigationRouter.goToExpenseForm()
            MainEvent.OnCategorySettingsItemClicked -> navigationRouter.goToCategorySettings()
            MainEvent.OnStatisticsItemClicked -> navigationRouter.goToExpenseStatistics()
        }
    }

    private fun onBottomBarEvent(event: MainBottomBarEvent) {
        when (event) {
            MainBottomBarEvent.ClearButtonClick -> viewState = viewState.copy(
                currentMonthYearKey = null
            )

            MainBottomBarEvent.FilterButtonClick -> viewState = viewState.copy(
                filterDialogVisible = true
            )

            MainBottomBarEvent.MenuButtonClick -> openDrawer?.invoke()
            MainBottomBarEvent.SearchButtonClick -> viewState = viewState.copy(
                topBarVisible = true,
            )
        }
    }

    private fun onFilterDialogEvent(event: MainFilterDialogEvent) {
        viewState = when (event) {
            MainFilterDialogEvent.CloseDialog -> viewState.copy(
                filterDialogVisible = false,
            )

            is MainFilterDialogEvent.OptionSelected -> viewState.copy(
                currentMonthYearKey = event.key,
                filterDialogVisible = false
            )

            MainFilterDialogEvent.ResetSelection -> viewState.copy(
                currentMonthYearKey = null,
                filterDialogVisible = false,
            )
        }
    }

    data class ViewState(
        val currentMonthYearKey: Expense.MonthYearKey? = null,
        val searchedTitle: String = "",
        val expenseToDelete: Expense? = null,
        private val expenses: List<Expense> = emptyList(),
        val topBarVisible: Boolean = false,
        val deleteDialogVisible: Boolean = false,
        val filterDialogVisible: Boolean = false,
    ) {
        val clearButtonVisible: Boolean get() = currentMonthYearKey != null
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val filteredExpenses: List<Expense>
            get() = expenses.filter(::checkIfHasKeyAndContainsSearchedTitle)

        private fun checkIfHasKeyAndContainsSearchedTitle(expense: Expense): Boolean =
            (currentMonthYearKey == null || expense.monthYearKey == currentMonthYearKey) && expense.title.contains(
                searchedTitle,
                true
            )

        val moneyInWalletAmount: Double
            get() = MathUtils.sumMoneyInList(
                expenses = filteredExpenses
            )
    }
}