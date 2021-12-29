package com.example.expensetrackerv2

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.MathUtils
import java.math.RoundingMode
import java.text.DecimalFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(expenseWithItsTypeList: List<ExpenseWithItsType>, navController: NavController) {
    val decimalFormat = DecimalFormat("#.##")
    decimalFormat.roundingMode = RoundingMode.CEILING

    LazyColumn(Modifier.padding(3.dp)) {
        expenseWithItsTypeList.groupBy { it.getKey() }.forEach { (_, expensesInSpecificDate) ->
            val calculateSumOfValue = { type: Type ->
                MathUtils.roundToMoney(expensesInSpecificDate.filter { it.type == type }
                    .map { it.price }.sum())
            }

            val incomeValue = decimalFormat.format(calculateSumOfValue(Type.INCOME))
            val outgoValue = decimalFormat.format(calculateSumOfValue(Type.OUTGO))

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
                            "-$outgoValue",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Red
                        )
                        Text("/", style = MaterialTheme.typography.subtitle1)
                        Text(
                            incomeValue,
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Green
                        )
                    }
                }
            }

            items(expensesInSpecificDate) { expense ->
                ExpenseCard(
                    expenseWithItsType = expense,
                    navController = navController
                )
            }
        }
    }
}