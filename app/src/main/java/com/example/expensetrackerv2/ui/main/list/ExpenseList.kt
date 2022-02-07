package com.example.expensetrackerv2.ui.main.list

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.ui.main.DeleteExpenseAlertDialog
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.MathUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    navController: NavController,
    expenseMonthYearKey: ExpenseMonthYearKey? = null,
    titleToSearch: String = ""
) {
    val expenseWithItsTypeList by expenseWithItsTypeRepository.getExpenses(expenseMonthYearKey, titleToSearch)
        .observeAsState(listOf())

    val isDeleteDialogOpen = remember { mutableStateOf(false) }
    val expenseWithItsTypeToDelete = remember { mutableStateOf(ExpenseWithItsType()) }

    val onDeleteButtonClick = { expenseWithItsType: ExpenseWithItsType ->
        expenseWithItsTypeToDelete.value = expenseWithItsType
        isDeleteDialogOpen.value = true
    }

    LazyColumn(Modifier.padding(3.dp)) {
        expenseWithItsTypeList.groupBy { it.getKey() }
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

    DeleteExpenseAlertDialog(
        isDeleteDialogOpen = isDeleteDialogOpen,
        expenseWithItsType = expenseWithItsTypeToDelete,
        expenseWithItsTypeRepository = expenseWithItsTypeRepository
    )
}