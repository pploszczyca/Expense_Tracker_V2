package com.example.expensetrackerv2.ui.main.features.list

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
import androidx.navigation.NavController
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewModel: ExpenseListViewModel,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithCategory) -> Unit,
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
                    expenseWithCategory = expense,
                    navController = navController,
                    onDeleteButtonClick = onDeleteButtonClick
                )
            }
        }
    }
}