package com.example.expensetrackerv2.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.ui.main.features.bottom_bar.MainBottomBarEvent
import com.example.expensetrackerv2.ui.main.features.filter_dialog.MainFilterDialogEvent
import com.example.expensetrackerv2.use_cases.expense.DeleteAllExpensesWithItsType
import com.example.expensetrackerv2.use_cases.expense.DeleteExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense.GetAllExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense.InsertExpenseWithCategory
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
    private val getAllExpenseWithCategory: GetAllExpenseWithCategory,
    private val deleteExpenseWithCategory: DeleteExpenseWithCategory,
    private val deleteAllExpensesWithItsType: DeleteAllExpensesWithItsType,
    private val insertExpenseWithCategory: InsertExpenseWithCategory,
    bottomBarChannel: Channel<MainBottomBarEvent>,
    filterDialogChannel: Channel<MainFilterDialogEvent>
) : ViewModel() {
    var viewState by mutableStateOf(ViewState())
        private set

    var openDrawer: (() -> Unit)? = null    // TODO: Think how to change it

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getAllExpenseWithCategory()
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
            is MainEvent.SearchedTitleChange -> viewState = viewState.copy(
                searchedTitle = event.value,
            )
            is MainEvent.ExportToJsonButtonClick -> exportToJson(event.value)
            is MainEvent.ImportFromJsonButtonClick -> importFromJsonAndInsert(event.value)
            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expenseToDelete?.let {
                        deleteExpenseWithCategory(expenseWithCategory = it)
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
                ).forEach { insertExpenseWithCategory(it) }
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
        val expenseToDelete: ExpenseWithCategory? = null,
        private val expensesWithItsType: List<ExpenseWithCategory> = emptyList(),
        val topBarVisible: Boolean = false,
        val deleteDialogVisible: Boolean = false,
        val filterDialogVisible: Boolean = false,
    ) {
        val clearButtonVisible: Boolean get() = currentMonthYearKey != null
        val mainExpenseInformationVisible: Boolean get() = topBarVisible.not()

        val filteredExpenses: List<ExpenseWithCategory>
            get() = expensesWithItsType.filter(::checkIfHasKeyAndContainsSearchedTitle)

        private fun checkIfHasKeyAndContainsSearchedTitle(expenseWithCategory: ExpenseWithCategory): Boolean =
            (currentMonthYearKey == null || expenseWithCategory.getKey() == currentMonthYearKey) && expenseWithCategory.title.contains(
                searchedTitle,
                true
            )

        val moneyInWalletAmount: Double
            get() = MathUtils.sumMoneyInList(
                expenseWithCategoryList = filteredExpenses
            )
    }
}