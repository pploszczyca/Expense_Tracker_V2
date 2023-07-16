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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.domain.Expense

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewModel: ExpenseListViewModel,
    onDeleteButtonClick: (Expense) -> Unit,
) {
    val expenseColor = Color(0xffff1744)
    val incomeColor = Color(0xff76ff03)

    val viewState by viewModel.viewState.collectAsState()

    LazyColumn(Modifier.padding(3.dp)) {
        viewState.dailyExpenses.forEach { dailyExpense ->
            stickyHeader {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        dailyExpense.date,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row {
                        Text(
                            dailyExpense.totalOutgo,
                            style = MaterialTheme.typography.labelMedium,
                            color = expenseColor
                        )
                        Text("/", style = MaterialTheme.typography.labelMedium)
                        Text(
                            dailyExpense.totalIncome,
                            style = MaterialTheme.typography.labelMedium,
                            color = incomeColor,
                        )
                    }
                }
            }

            items(
                items = dailyExpense.expenses,
                key = Expense::id,
            ) { expense ->
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