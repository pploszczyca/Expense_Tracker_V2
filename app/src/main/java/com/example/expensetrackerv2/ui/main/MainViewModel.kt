package com.example.expensetrackerv2.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.State
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val getExpensesWithItsType: GetExpensesWithItsType,
    private val deleteExpenseWithItsType: DeleteExpenseWithItsType,
    private val deleteAllExpensesWithItsType: DeleteAllExpensesWithItsType,
    private val insertExpenseWithItsType: InsertExpenseWithItsType
) : ViewModel() {
    private val _currentMonthYearKey = mutableStateOf<ExpenseMonthYearKey?>(null)
    val currentMonthYearKey: State<ExpenseMonthYearKey?> = _currentMonthYearKey

    private val _searchedTitle = mutableStateOf("")
    val searchedTitle: State<String> = _searchedTitle

    private val _expenseToDelete = mutableStateOf(ExpenseWithItsType())
    private val expenseToDelete: State<ExpenseWithItsType> = _expenseToDelete

    val expensesWithItsType: Flow<List<ExpenseWithItsType>> = getExpensesWithItsType()

    private val _isTopBarVisible = mutableStateOf(false)
    val isTopBarVisible: State<Boolean> = _isTopBarVisible

    private val _isDeleteDialogVisible = mutableStateOf(false)
    val isDeleteDialogVisible: State<Boolean> = _isDeleteDialogVisible

    fun isClearButtonVisible() = currentMonthYearKey.value != null
    fun isMainExpenseInformationVisible() = isTopBarVisible.value.not()

    private fun exportToJson(uri: Uri?) {
        uri?.let { uri ->
            viewModelScope.launch {
                contentResolver.openOutputStream(uri)
                    ?.write(
                        JSONUtils.exportExpensesListToJson(expensesWithItsType.first())
                            .toByteArray()
                    )
            }
        }
    }

    private fun importFromJsonAndInsert(uri: Uri?) {
        uri?.let { uri ->
            viewModelScope.launch {
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
        _isDeleteDialogVisible.value = value
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.MonthYearKeyChange -> _currentMonthYearKey.value = event.value
            is MainEvent.SearchedTitleChange -> _searchedTitle.value = event.value
            is MainEvent.TopBarVisibilityChange -> _isTopBarVisible.value = event.value
            is MainEvent.ExportToJsonButtonClick -> exportToJson(event.value)
            is MainEvent.ImportFromJsonButtonClick -> importFromJsonAndInsert(event.value)
            is MainEvent.ConfirmDeleteButtonClick -> {
                viewModelScope.launch {
                    deleteExpenseWithItsType(expenseToDelete.value)
                }
                changeDeleteDialogVisibility(false)
            }
            is MainEvent.DeleteButtonClick -> {
                _expenseToDelete.value = event.value
                changeDeleteDialogVisibility(true)
            }
            is MainEvent.DismissDeleteButtonClick -> changeDeleteDialogVisibility(false)
        }
    }


}