package com.example.expensetrackerv2.ui.main.features.filter_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.MonthYearKey
import com.example.expensetrackerv2.database.models.getKey
import com.example.expensetrackerv2.use_cases.expense.GetExpenses
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainFilterDialogViewModel @Inject constructor(
    private val channel: Channel<MainFilterDialogEvent>,
    getExpenses: GetExpenses,
) : ViewModel() {

    var viewState by mutableStateOf(ViewState())
        private set

    init {
        viewModelScope.launch {
            getExpenses()
                .map { expenses ->
                    expenses.map { it.getKey() }
                }.distinctUntilChanged()
                .collect { keys ->
                    viewState = viewState.copy(
                        options = keys.map {
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
        val options: List<FilterOption> = emptyList()
    ) {
        data class FilterOption(
            val key: MonthYearKey
        ) {
            val dateText: String = DateUtils.dateToStringWithMonthAndYear(
                date = Date(
                    key.year,
                    key.month,
                    1
                )
            )
        }
    }
}