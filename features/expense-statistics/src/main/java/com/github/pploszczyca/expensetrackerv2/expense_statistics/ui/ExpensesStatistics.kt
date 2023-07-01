package com.github.pploszczyca.expensetrackerv2.expense_statistics.ui

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
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toFormattedString
import com.github.pploszczyca.expensetrackerv2.common_ui.bar.TopAppBarWithBack
import com.github.pploszczyca.expensetrackerv2.common_ui.calendar_field.CalendarDialogField
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.expense_statistics.utilities.MathUtils
import com.github.pploszczyca.expensetrackerv2.expense_statistics.view_model.ExpensesStatisticsViewModel
import com.github.pploszczyca.expensetrackerv2.features.expense_statistics.R

@Composable
fun ExpensesStatistics(
    viewModel: ExpensesStatisticsViewModel,
) {
    // TODO: Move theme to another module?
    val expenseColor = Color(0xffff1744)
    val incomeColor = Color(0xff76ff03)

    val fromDate = viewModel.fromDate
    val toDate = viewModel.toDate
    val expenseWithItsTypeFilteredList =
        viewModel.expensesWithItsType.collectAsState(
            emptyList()
        ).value.filter { it.date.toFormattedString() in fromDate.value..toDate.value }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.expense_statistics_app_bar_title),
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
                    color = expenseColor
                )

                StatisticsCard(
                    title = stringResource(id = R.string.total_incomes),
                    number = MathUtils.sumMoneyInListByTypeToString(
                        expenseWithItsTypeFilteredList,
                        Category.Type.INCOME
                    ),
                    color = incomeColor
                )
            }
        },
    )
}

