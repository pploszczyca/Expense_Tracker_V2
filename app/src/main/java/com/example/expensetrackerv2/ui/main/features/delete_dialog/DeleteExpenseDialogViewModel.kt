package com.example.expensetrackerv2.ui.main.features.delete_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense_with_category.DeleteExpenseWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteExpenseDialogViewModel @Inject constructor(
    private val deleteExpenseWithCategory: DeleteExpenseWithCategory
) : ViewModel() {
    private var viewState by mutableStateOf(ViewState())

    fun init(expenseWithCategory: ExpenseWithCategory) {
        viewState = viewState.copy(
            expenseWithCategory = expenseWithCategory
        )
    }

    fun onEvent(event: DeleteExpenseDialogEvent) {
        when (event) {
            DeleteExpenseDialogEvent.ConfirmButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expenseWithCategory?.let {
                        deleteExpenseWithCategory(
                            expenseWithCategory = it
                        )
                    }
                }
            }
        }
    }

    data class ViewState(
        val expenseWithCategory: ExpenseWithCategory? = null
    )
}