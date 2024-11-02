package com.github.pploszczyca.expensetrackerv2.expense_form.features.delete_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteExpenseDialogViewModel @Inject constructor(
    private val deleteExpense: DeleteExpense,
    private val dispatchersProvider: DispatcherProvider,
) : ViewModel() {
    private var viewState by mutableStateOf(ViewState())

    fun onEvent(event: DeleteExpenseDialogEvent) {
        viewModelScope.launch(dispatchersProvider.default) {
            when (event) {
                DeleteExpenseDialogEvent.ConfirmButtonClick -> {
                    viewState.expense?.let {
                        deleteExpense(expense = it)
                    }
                }

                is DeleteExpenseDialogEvent.ExpenseChanged -> {
                    viewState = viewState.copy(
                        expense = event.expense
                    )
                }
            }
        }
    }

    data class ViewState(
        val expense: Expense? = null,
    )
}