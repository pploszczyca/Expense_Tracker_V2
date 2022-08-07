package com.example.expensetrackerv2.ui.main.features.delete_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.DeleteExpenseWithItsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteExpenseDialogViewModel @Inject constructor(
    private val deleteExpenseWithItsType: DeleteExpenseWithItsType
) : ViewModel() {
    private var viewState by mutableStateOf(ViewState())

    fun init(expenseWithItsType: ExpenseWithItsType) {
        viewState = viewState.copy(
            expenseWithItsType = expenseWithItsType
        )
    }

    fun onEvent(event: DeleteExpenseDialogEvent) {
        when(event) {
            DeleteExpenseDialogEvent.ConfirmButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expenseWithItsType?.let {
                        deleteExpenseWithItsType(
                            expenseWithItsType = it
                        )
                    }
                }
            }
        }
    }

    data class ViewState(
        val expenseWithItsType: ExpenseWithItsType? = null
    )
}