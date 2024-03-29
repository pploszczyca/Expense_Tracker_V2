package com.example.expensetrackerv2.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.extensions.toFormattedString
import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.ui.common_components.bar.TopAppBarWithBack
import com.example.expensetrackerv2.ui.common_components.calendar_field.CalendarDialogField
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.utilities.MathUtils

@Composable
fun ExpensesStatistics(
    navController: NavController,
    expensesStatisticsViewModel: ExpensesStatisticsViewModel,
) {
    val fromDate = expensesStatisticsViewModel.fromDate
    val toDate = expensesStatisticsViewModel.toDate
    val expenseWithItsTypeFilteredList =
        expensesStatisticsViewModel.expensesWithItsType.collectAsState(
            emptyList()
        ).value.filter { it.date.toFormattedString() in fromDate.value..toDate.value }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_statistics),
                onBackClicked = navController::navigateUp,
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                CalendarDialogField(
                    date = fromDate.value,
                    label = stringResource(id = R.string.from_date),
                    onDatePickerPick = { dateFromDialog ->
                        expensesStatisticsViewModel.onFromDateChange(
                            dateFromDialog
                        )
                    })

                CalendarDialogField(
                    date = toDate.value,
                    label = stringResource(id = R.string.to_date),
                    onDatePickerPick = { dateFromDialog ->
                        expensesStatisticsViewModel.onToDateChange(
                            dateFromDialog
                        )
                    })

                Spacer(modifier = Modifier.padding(4.dp))

                StatisticsCard(
                    title = stringResource(id = R.string.total),
                    number = MathUtils.sumMoneyInListToString(expenseWithItsTypeFilteredList),
                    color = Color.Unspecified
                )

                StatisticsCard(
                    title = stringResource(id = R.string.total_expenses),
                    number = MathUtils.sumMoneyInListByTypeToString(
                        expenseWithItsTypeFilteredList,
                        CategoryType.OUTGO
                    ),
                    color = ExpenseColor
                )

                StatisticsCard(
                    title = stringResource(id = R.string.total_incomes),
                    number = MathUtils.sumMoneyInListByTypeToString(
                        expenseWithItsTypeFilteredList,
                        CategoryType.INCOME
                    ),
                    color = IncomeColor
                )
            }
        },
    )
}

