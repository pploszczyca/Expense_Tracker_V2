package com.example.expensetrackerv2.ui.main.features.filter_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.extensions.toStringDate
import com.example.expensetrackerv2.models.getKey
import com.example.expensetrackerv2.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.use_cases.expense.GetAllExpenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFilterDialogViewModel @Inject constructor(
    private val channel: Channel<MainFilterDialogEvent>,
    getAllExpenses: GetAllExpenses,
) : ViewModel() {

    var viewState by mutableStateOf(ViewState())
        private set

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getAllExpenses()
                .map { expenses ->
                    expenses.map { it.getKey() }
                }
                .collect { keys ->
                    viewState = viewState.copy(
                        options = keys
                            .distinct()
                            .map {
                                ViewState.FilterOption(
                                    key = it
                                )
                            }
                    )
                }
        }
    }

    fun onEvent(event: MainFilterDialogEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            channel.send(event)
        }
    }

    data class ViewState(
        val options: List<FilterOption> = emptyList(),
    ) {
        data class FilterOption(
            val key: ExpenseMonthYearKey,
        ) {
            val dateText: String = key.toStringDate()
        }
    }
}