package com.example.expensetrackerv2.ui

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.MathUtils
import java.math.RoundingMode
import java.text.DecimalFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    expenseWithItsTypeList: MutableState<List<ExpenseWithItsType>>,
    navController: NavController,
    expenseDao: ExpenseDao
) {
    val decimalFormat = DecimalFormat("#.##")
    decimalFormat.roundingMode = RoundingMode.CEILING

    val isDeleteDialogOpen = remember { mutableStateOf(false) }
    val expenseWithItsTypeToDelete = remember { mutableStateOf(ExpenseWithItsType()) }

    val onDeleteButtonClick = { expenseWithItsType: ExpenseWithItsType ->
        expenseWithItsTypeToDelete.value = expenseWithItsType
        isDeleteDialogOpen.value = true
    }

    LazyColumn(Modifier.padding(3.dp)) {
        expenseWithItsTypeList.value.groupBy { it.getKey() }
            .forEach { (_, expensesInSpecificDate) ->
                val filterExpenseListByType =
                    { type: Type -> expensesInSpecificDate.filter { it.type == type } }

                val incomeValue =
                    decimalFormat.format(MathUtils.sumMoneyInList(filterExpenseListByType(Type.INCOME)))
                val outgoValue =
                    decimalFormat.format(MathUtils.sumMoneyInList(filterExpenseListByType(Type.OUTGO)))

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

    DeleteExpenseAlertDialog(
        isDeleteDialogOpen = isDeleteDialogOpen,
        expenseDao = expenseDao,
        expenseWithItsType = expenseWithItsTypeToDelete
    )
}