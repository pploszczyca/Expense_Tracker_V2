package com.github.pploszczyca.expensetrackerv2.features.main.features.delete_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteExpenseDialogViewModel @Inject constructor(
    private val deleteExpense: DeleteExpense,
) : ViewModel() {
    private var viewState by mutableStateOf(ViewState())

    fun init(expense: Expense) {
        viewState = viewState.copy(
            expense = expense
        )
    }

    fun onEvent(event: DeleteExpenseDialogEvent) {
        when (event) {
            DeleteExpenseDialogEvent.ConfirmButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewState.expense?.let {
                        deleteExpense(expense = it)
                    }
                }
            }
        }
    }

    data class ViewState(
        val expense: Expense? = null,
    )
}