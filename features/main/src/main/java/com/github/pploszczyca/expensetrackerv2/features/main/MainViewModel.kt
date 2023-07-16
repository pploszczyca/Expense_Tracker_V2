package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.MainBottomBarEvent
import com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog.MainFilterDialogEvent
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetAllExpenses
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.GetExpenseSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExpenseSummary: GetExpenseSummary,
    private val deleteExpense: DeleteExpense,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    filterDialogChannel: Channel<MainFilterDialogEvent>,
    private val navigationRouter: NavigationRouter,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    var openDrawer: (() -> Unit)? = null    // TODO: Think how to change it

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getExpenseSummary()
                .collect { expenseSummary ->
                    _viewState.updateTransform {
                        copy(expenseSummary = expenseSummary)
                    }
                }
        }

        viewModelScope.launch(Dispatchers.Default) {
            bottomBarChannel.consumeEach(::onBottomBarEvent)
        }

        viewModelScope.launch(Dispatchers.Default) {
            filterDialogChannel.consumeEach(::onFilterDialogEvent)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchedTitleChange ->
                _viewState.updateTransform {
                    copy(searchedTitle = event.value)
                }

            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _viewState.value.expenseToDelete?.let {
                        deleteExpense(expense = it)
                    }
                }
                _viewState.updateTransform {
                    copy(deleteDialogVisible = false)
                }
            }

            is MainEvent.DeleteButtonClick -> _viewState.updateTransform {
                copy(
                    expenseToDelete = event.value,
                    deleteDialogVisible = true,
                )
            }

            is MainEvent.DismissDeleteButtonClick -> _viewState.updateTransform {
                copy(deleteDialogVisible = false)
            }

            is MainEvent.OnTopBarTrailingIconClick -> _viewState.updateTransform {
                copy(
                    topBarVisible = false,
                    searchedTitle = "",
                )
            }

            MainEvent.OnAddNewExpenseButtonClicked -> navigationRouter.goToExpenseForm()
            MainEvent.OnCategorySettingsItemClicked -> navigationRouter.goToCategorySettings()
            MainEvent.OnStatisticsItemClicked -> navigationRouter.goToExpenseStatistics()
        }
    }

    private fun onBottomBarEvent(event: MainBottomBarEvent) {
        when (event) {
            MainBottomBarEvent.ClearButtonClick -> _viewState.updateTransform {
                copy(currentMonthYearKey = null)
            }

            MainBottomBarEvent.FilterButtonClick -> _viewState.updateTransform {
                copy(filterDialogVisible = true)
            }

            MainBottomBarEvent.MenuButtonClick -> openDrawer?.invoke()
            MainBottomBarEvent.SearchButtonClick -> _viewState.updateTransform {
                copy(topBarVisible = true)
            }
        }
    }

    private fun onFilterDialogEvent(event: MainFilterDialogEvent) {
        _viewState.updateTransform {
            when (event) {
                MainFilterDialogEvent.CloseDialog -> copy(
                    filterDialogVisible = false,
                )

                is MainFilterDialogEvent.OptionSelected -> copy(
                    currentMonthYearKey = event.key,
                    filterDialogVisible = false
                )

                MainFilterDialogEvent.ResetSelection -> copy(
                    currentMonthYearKey = null,
                    filterDialogVisible = false,
                )
            }
        }
    }

    data class ViewState(
        val currentMonthYearKey: Expense.MonthYearKey? = null,
        val searchedTitle: String = "",
        val expenseToDelete: Expense? = null,
        val expenseSummary: ExpenseSummary? = null,
        val topBarVisible: Boolean = false,
        val deleteDialogVisible: Boolean = false,
        val filterDialogVisible: Boolean = false,
    ) {
        val clearButtonVisible: Boolean get() = currentMonthYearKey != null
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val moneyInWalletAmount: Double
            get() = expenseSummary?.let { it.totalIncome - it.totalOutgo } ?: 0.0
    }
}