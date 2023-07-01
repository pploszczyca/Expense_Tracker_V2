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
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.extensions.toFormattedString
import com.example.expensetrackerv2.ui.common_components.bar.TopAppBarWithBack
import com.example.expensetrackerv2.ui.common_components.calendar_field.CalendarDialogField
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.utilities.MathUtils
import com.github.pploszczyca.expensetrackerv2.domain.Category

@Composable
fun ExpensesStatistics(
    viewModel: ExpensesStatisticsViewModel,
) {
    val fromDate = viewModel.fromDate
    val toDate = viewModel.toDate
    val expenseWithItsTypeFilteredList =
        viewModel.expensesWithItsType.collectAsState(
            emptyList()
        ).value.filter { it.date.toFormattedString() in fromDate.value..toDate.value }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_statistics),
                onBackClicked = viewModel::onBackButtonClicked,
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                CalendarDialogField(
                    date = fromDate.value,
                    label = stringResource(id = R.string.from_date),
                    onDatePickerPick = { dateFromDialog ->
                        viewModel.onFromDateChange(
                            dateFromDialog
                        )
                    })

                CalendarDialogField(
                    date = toDate.value,
                    label = stringResource(id = R.string.to_date),
                    onDatePickerPick = { dateFromDialog ->
                        viewModel.onToDateChange(
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
                        Category.Type.OUTGO
                    ),
                    color = ExpenseColor
                )

                StatisticsCard(
                    title = stringResource(id = R.string.total_incomes),
                    number = MathUtils.sumMoneyInListByTypeToString(
                        expenseWithItsTypeFilteredList,
                        Category.Type.INCOME
                    ),
                    color = IncomeColor
                )
            }
        },
    )
}

