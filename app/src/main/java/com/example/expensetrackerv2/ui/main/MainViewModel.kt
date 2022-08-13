package com.example.expensetrackerv2.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.ui.main.features.bottom_bar.MainBottomBarEvent
import com.example.expensetrackerv2.ui.main.features.filter_dialog.MainFilterDialogEvent
import com.example.expensetrackerv2.use_cases.expense.DeleteAllExpensesWithItsType
import com.example.expensetrackerv2.use_cases.expense.DeleteExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.GetExpensesWithItsType
import com.example.expensetrackerv2.use_cases.expense.InsertExpenseWithItsType
import com.example.expensetrackerv2.utilities.JSONUtils
import com.example.expensetrackerv2.utilities.MathUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val getExpensesWithItsType: GetExpensesWithItsType,
    private val deleteExpenseWithItsType: DeleteExpenseWithItsType,
    private val deleteAllExpensesWithItsType: DeleteAllExpensesWithItsType,
    private val insertExpenseWithItsType: InsertExpenseWithItsType,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    filterDialogChannel: Channel<MainFilterDialogEvent>
) : ViewModel() {
    var viewState by mutableStateOf(ViewState())
        private set

    var openDrawer: (() -> Unit)? = null    // TODO: Think how to change it

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getExpensesWithItsType()
                .collect {
                    viewState = viewState.copy(
                        expensesWithItsType = it
                    )
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
            is MainEvent.MonthYearKeyChange -> viewState = viewState.copy(
                currentMonthYearKey = event.value,
            )
            is MainEvent.SearchedTitleChange -> viewState = viewState.copy(
                searchedTitle = event.value,
            )
            is MainEvent.ExportToJsonButtonClick -> exportToJson(event.value)
            is MainEvent.ImportFromJsonButtonClick -> importFromJsonAndInsert(event.value)
            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expenseToDelete?.let {
                        deleteExpenseWithItsType(expenseWithItsType = it)
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
        }
    }

    private fun exportToJson(uri: Uri?) {
        uri?.let { uri ->
            viewModelScope.launch(Dispatchers.IO) {
                contentResolver.openOutputStream(uri)
                    ?.write(
                        JSONUtils
                            .exportExpensesListToJson(viewState.filteredExpenses)
                            .toByteArray()
                    )
            }
        }
    }

    private fun importFromJsonAndInsert(uri: Uri?) {
        uri?.let { uri ->
            viewModelScope.launch(Dispatchers.IO) {
                deleteAllExpensesWithItsType()
                JSONUtils.importExpensesListFromJson(
                    contentResolver.openInputStream(
                        uri
                    )?.bufferedReader()?.use { it.readText() }!!
                ).forEach { insertExpenseWithItsType(it) }
            }
        }
    }

    private fun onBottomBarEvent(event: MainBottomBarEvent) {
        when(event) {
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
        viewState = when(event) {
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
        val currentMonthYearKey: ExpenseMonthYearKey? = null,
        val searchedTitle: String = "",
        val expenseToDelete: ExpenseWithItsType? = null,
        private val expensesWithItsType: List<ExpenseWithItsType> = emptyList(),
        val topBarVisible: Boolean = false,
        val deleteDialogVisible: Boolean = false,
        val filterDialogVisible: Boolean = false,
    ) {
        val clearButtonVisible: Boolean get() = currentMonthYearKey != null
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val filteredExpenses: List<ExpenseWithItsType>
            get() = expensesWithItsType.filter {
                checkIfHasKeyAndContainsSearchedTitle(
                    it
                )
            }

        private fun checkIfHasKeyAndContainsSearchedTitle(expenseWithItsType: ExpenseWithItsType): Boolean =
            (currentMonthYearKey == null || expenseWithItsType.getKey() == currentMonthYearKey) && expenseWithItsType.title.contains(
                searchedTitle,
                true
            )

        val monthYearKeys: List<ExpenseMonthYearKey>
            get() = filteredExpenses.map { it.getKey() }.distinct()
        val moneyInWalletAmount: Double
            get() = MathUtils.sumMoneyInList(
                expenseWithItsTypeList = filteredExpenses
            )
    }
}