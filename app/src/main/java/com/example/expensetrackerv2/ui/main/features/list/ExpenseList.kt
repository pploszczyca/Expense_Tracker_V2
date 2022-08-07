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
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.ui.main.MainViewModel
import com.example.expensetrackerv2.ui.main.features.delete_dialog.DeleteExpenseAlertDialog
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.MathUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    mainViewState: MainViewModel.ViewState,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithItsType) -> Unit,
    onDismissDeleteButtonClick: () -> Unit,
    onConfirmDeleteButtonClick: () -> Unit,
) {
    LazyColumn(Modifier.padding(3.dp)) {
        mainViewState.filteredExpenses
            .groupBy { it.getKey() }
            .forEach { (_, expensesInSpecificDate) ->
                val filterExpenseListByType =
                    { type: Type -> expensesInSpecificDate.filter { it.type == type } }

                val incomeValue =
                    MathUtils.sumMoneyInListToString(filterExpenseListByType(Type.INCOME))
                val outgoValue =
                    MathUtils.sumMoneyInListToString(filterExpenseListByType(Type.OUTGO))

                stickyHeader {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.background)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            DateUtils.dateToStringWithMonthAndYear(date = expensesInSpecificDate.first().date),
                            style = MaterialTheme.typography.subtitle1
                        )

                        Row {
                            Text(
                                outgoValue,
                                style = MaterialTheme.typography.subtitle1,
                                color = ExpenseColor
                            )
                            Text("/", style = MaterialTheme.typography.subtitle1)
                            Text(
                                incomeValue,
                                style = MaterialTheme.typography.subtitle1,
                                color = IncomeColor
                            )
                        }
                    }
                }

                items(expensesInSpecificDate) { expense ->
                    ExpenseCard(
                        expenseWithItsType = expense,
                        navController = navController,
                        onDeleteButtonClick = onDeleteButtonClick
                    )
                }
            }
    }

    if (mainViewState.isDeleteDialogVisible) {
        DeleteExpenseAlertDialog(
            onDismissClick = onDismissDeleteButtonClick,
            onConfirmButtonClick = onConfirmDeleteButtonClick
        )
    }

}