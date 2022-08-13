package com.example.expensetrackerv2.ui.main.features.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewModel: ExpenseListViewModel,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithItsType) -> Unit,
) {
    val viewState = viewModel.viewState

    LazyColumn(Modifier.padding(3.dp)) {
        viewState.groupedExpensesList.forEach { groupedExpenses ->
            stickyHeader {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        groupedExpenses.dateText,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Row {
                        Text(
                            groupedExpenses.totalOutgo,
                            style = MaterialTheme.typography.subtitle1,
                            color = ExpenseColor
                        )
                        Text("/", style = MaterialTheme.typography.subtitle1)
                        Text(
                            groupedExpenses.totalIncome,
                            style = MaterialTheme.typography.subtitle1,
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
                    expenseWithItsType = expense,
                    navController = navController,
                    onDeleteButtonClick = onDeleteButtonClick
                )
            }
        }
    }
}