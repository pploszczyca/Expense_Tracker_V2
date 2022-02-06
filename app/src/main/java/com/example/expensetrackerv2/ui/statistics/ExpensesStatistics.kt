package com.example.expensetrackerv2.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.ui.bar.TopAppBarWithBack
import com.example.expensetrackerv2.ui.form.CalendarDialogField
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.MathUtils
import java.util.*

@Composable
fun ExpensesStatistics(
    navController: NavController,
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository
) {
    var fromDate by remember {
        mutableStateOf(DateUtils.toOnlyDateString(Date()))
    }
    var toDate by remember {
        mutableStateOf(DateUtils.toOnlyDateString(Date()))
    }
    val expenseWithItsTypeFilteredList = expenseWithItsTypeRepository.getExpenses()
        .observeAsState(listOf()).value.filter { DateUtils.toOnlyDateString(it.date) in fromDate..toDate }

    Scaffold(content = {
        Column(modifier = Modifier.fillMaxSize()) {
            CalendarDialogField(
                date = fromDate,
                label = stringResource(id = R.string.from_date),
                onDatePickerPick = { dateFromDialog -> fromDate = dateFromDialog.toString() })

            CalendarDialogField(
                date = toDate,
                label = stringResource(id = R.string.to_date),
                onDatePickerPick = { dateFromDialog -> toDate = dateFromDialog.toString() })

            Spacer(modifier = Modifier.padding(4.dp))

            StatisticsCard(
                title = stringResource(id = R.string.total),
                number = MathUtils.sumMoneyInListToString(expenseWithItsTypeFilteredList),
                color = Color.Unspecified
            )

            StatisticsCard(
                title = stringResource(id = R.string.total_expenses),
                number = MathUtils.sumMoneyInListToString(expenseWithItsTypeFilteredList.filter { it.type == Type.OUTGO }),
                color = ExpenseColor
            )

            StatisticsCard(
                title = stringResource(id = R.string.total_incomes),
                number = MathUtils.sumMoneyInListToString(expenseWithItsTypeFilteredList.filter { it.type == Type.INCOME }),
                color = IncomeColor
            )
        }
    },
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_statistics),
                navController = navController
            )
        }
    )
}

