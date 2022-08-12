package com.example.expensetrackerv2.ui.main.features.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.use_cases.expense.GetExpensesWithItsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    getExpensesWithItsType: GetExpensesWithItsType,
    mapper: ExpensesListGroupedExpensesMapper,
) : ViewModel(){
    var viewState by mutableStateOf(ViewState())
        private set

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getExpensesWithItsType()
                .collect {
                    viewState = viewState.copy(
                        groupedExpensesList = mapper.map(it)
                    )
                }
        }
    }


    data class ViewState(
        val groupedExpensesList: List<GroupedExpenses> = emptyList()
    ) {
        data class GroupedExpenses(
            val key: ExpenseMonthYearKey,
            val expenses: List<ExpenseWithItsType>,
            val totalIncome: String,
            val totalOutgo: String,
        )
    }
}