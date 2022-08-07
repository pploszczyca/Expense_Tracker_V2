package com.example.expensetrackerv2.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.DeleteAllExpensesWithItsType
import com.example.expensetrackerv2.use_cases.expense.DeleteExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.GetExpensesWithItsType
import com.example.expensetrackerv2.use_cases.expense.InsertExpenseWithItsType
import com.example.expensetrackerv2.utilities.JSONUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val getExpensesWithItsType: GetExpensesWithItsType,
    private val deleteExpenseWithItsType: DeleteExpenseWithItsType,
    private val deleteAllExpensesWithItsType: DeleteAllExpensesWithItsType,
    private val insertExpenseWithItsType: InsertExpenseWithItsType,
    private val stateMapper: MainStateMapper,
) : ViewModel() {
    private val viewModelState by mutableStateOf(ViewModelState())
    val viewState = stateMapper.toViewState(viewModelState)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getExpensesWithItsType()
                .collect {
                    viewModelState.expensesWithItsType = it
                }
        }
    }

    private fun exportToJson(uri: Uri?) {
        uri?.let { uri ->
            viewModelScope.launch(Dispatchers.IO) {
                contentResolver.openOutputStream(uri)
                    ?.write(
                        JSONUtils
                            .exportExpensesListToJson(viewModelState.expensesWithItsType.orEmpty())
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

    private fun changeDeleteDialogVisibility(value: Boolean) {
        viewModelState.isDeleteDialogVisible = value
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.MonthYearKeyChange -> viewModelState.currentMonthYearKey = event.value
            is MainEvent.SearchedTitleChange -> viewModelState.searchedTitle = event.value
            is MainEvent.TopBarVisibilityChange -> viewModelState.isTopBarVisible = event.value
            is MainEvent.ExportToJsonButtonClick -> exportToJson(event.value)
            is MainEvent.ImportFromJsonButtonClick -> importFromJsonAndInsert(event.value)
            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewModelState.expenseToDelete?.let {
                        deleteExpenseWithItsType(expenseWithItsType = it)
                    }

                }
                changeDeleteDialogVisibility(false)
            }
            is MainEvent.DeleteButtonClick -> {
                viewModelState.expenseToDelete = event.value
                changeDeleteDialogVisibility(true)
            }
            is MainEvent.DismissDeleteButtonClick -> changeDeleteDialogVisibility(false)
        }
    }

    data class ViewModelState(
        var currentMonthYearKey: ExpenseMonthYearKey? = null,
        var searchedTitle: String = "",
        var expenseToDelete: ExpenseWithItsType? = null,
        var expensesWithItsType: List<ExpenseWithItsType>? = null,
        var isTopBarVisible: Boolean = false,
        var isDeleteDialogVisible: Boolean = false
    )

    data class ViewState(
        val currentMonthYearKey: ExpenseMonthYearKey?,
        val searchedTitle: String,
        val expenseToDelete: ExpenseWithItsType?,
        val expensesWithItsType: List<ExpenseWithItsType>,
        val isTopBarVisible: Boolean,
        val isDeleteDialogVisible: Boolean,
        val clearButtonVisible: Boolean,
        val mainExpenseInformationVisible: Boolean
    )
}