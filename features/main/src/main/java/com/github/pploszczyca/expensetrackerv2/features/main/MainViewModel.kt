package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.domain.orZero
import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.MainBottomBarEvent
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.GetExpenseSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExpenseSummary: GetExpenseSummary,
    private val deleteExpense: DeleteExpense,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    private val navigationRouter: NavigationRouter,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    var openDrawer: (() -> Unit)? = null    // TODO: Think how to change it

    init {
        viewModelScope.launch(dispatcherProvider.default) {
            getExpenseSummary()
                .collect { expenseSummary ->
                    _viewState.updateTransform {
                        copy(expenseSummary = expenseSummary)
                    }
                }
        }

        viewModelScope.launch(dispatcherProvider.default) {
            bottomBarChannel.consumeEach(::onBottomBarEvent)
        }
    }

    fun onEvent(event: MainEvent) {
        viewModelScope.launch(dispatcherProvider.default) {
            when (event) {
                is MainEvent.SearchedTitleChange ->
                    _viewState.updateTransform {
                        copy(searchedTitle = event.value)
                    }

                is MainEvent.ConfirmDeleteButtonClick -> {
                    _viewState.value.expenseToDelete?.let {
                        deleteExpense(expense = it)
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
    }

    private fun onBottomBarEvent(event: MainBottomBarEvent) {
        when (event) {
            MainBottomBarEvent.MenuButtonClick -> openDrawer?.invoke()
            MainBottomBarEvent.SearchButtonClick -> _viewState.updateTransform {
                copy(topBarVisible = true)
            }
        }
    }

    data class ViewState(
        val searchedTitle: String = "",
        val expenseToDelete: Expense? = null,
        val expenseSummary: ExpenseSummary? = null,
        val topBarVisible: Boolean = false,
        val deleteDialogVisible: Boolean = false,
    ) {
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val moneyInWalletAmount: Price
            get() = expenseSummary?.let { it.totalIncome - it.totalOutgo }.orZero()
    }
}