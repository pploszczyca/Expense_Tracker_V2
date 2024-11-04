package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.domain.orZero
import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.MainBottomBarEvent
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.GetExpenseSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExpenseSummary: GetExpenseSummary,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    private val navigationRouter: NavigationRouter,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    private val _routeAction: MutableSharedFlow<RouteAction> = MutableSharedFlow()
    val routeAction: Flow<RouteAction> get() = _routeAction

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
            bottomBarChannel.consumeEach {
                onBottomBarEvent(it)
            }
        }
    }

    fun onEvent(event: MainEvent) {
        viewModelScope.launch(dispatcherProvider.default) {
            when (event) {
                is MainEvent.SearchedTitleChange ->
                    _viewState.updateTransform {
                        copy(searchedTitle = event.value)
                    }

                is MainEvent.OnTopBarTrailingIconClick -> _viewState.updateTransform {
                    copy(
                        topBarVisible = false,
                        searchedTitle = "",
                    )
                }

                MainEvent.OnAddNewExpenseButtonClicked -> navigationRouter.goToExpenseForm()
                MainEvent.OnCategorySettingsItemClicked -> {
                    _routeAction.emit(RouteAction.CloseDrawer)
                    navigationRouter.goToCategorySettings()
                }
                MainEvent.OnStatisticsItemClicked -> {
                    _routeAction.emit(RouteAction.CloseDrawer)
                    navigationRouter.goToExpenseStatistics()
                }
            }
        }
    }

    private suspend fun onBottomBarEvent(event: MainBottomBarEvent) {
        when (event) {
            MainBottomBarEvent.MenuButtonClick ->
                _routeAction.emit(RouteAction.OpenDrawer)
            MainBottomBarEvent.SearchButtonClick -> _viewState.updateTransform {
                copy(topBarVisible = true)
            }
        }
    }

    data class ViewState(
        val searchedTitle: String = "",
        val expenseSummary: ExpenseSummary? = null,
        val topBarVisible: Boolean = false,
    ) {
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val moneyInWalletAmount: Price
            get() = expenseSummary?.total.orZero()
    }

    sealed interface RouteAction {
        data object OpenDrawer : RouteAction
        data object CloseDrawer : RouteAction
    }
}