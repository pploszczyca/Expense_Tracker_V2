package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Expense

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewModel: ExpenseListViewModel,
    onDeleteButtonClick: (Expense) -> Unit,
) {
    val viewState = viewModel.viewState

    LazyColumn(Modifier.padding(3.dp)) {
        viewState.groupedExpensesList.forEach { groupedExpenses ->
            stickyHeader {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        groupedExpenses.dateText,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row {
                        Text(
                            groupedExpenses.totalOutgo,
                            style = MaterialTheme.typography.labelMedium,
                            color = ExpenseColor
                        )
                        Text("/", style = MaterialTheme.typography.labelMedium)
                        Text(
                            groupedExpenses.totalIncome,
                            style = MaterialTheme.typography.labelMedium,
                            color = IncomeColor
                        )
                    }
                }
            }

            items(items = groupedExpenses.expenses,
                key = {
                    it.id
                }) { expense ->
                ExpenseCard(
                    expense = expense,
                    onDeleteButtonClick = onDeleteButtonClick,
                    onEditExpenseButtonClicked = {
                        viewModel.onEvent(ExpensesListEvent.OnEditExpenseButtonClicked(it))
                    },
                )
            }
        }
    }
}