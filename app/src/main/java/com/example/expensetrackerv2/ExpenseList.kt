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
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Type
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.models.getKey
import com.example.expensetrackerv2.utilities.DateUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(expenses: List<Expense>, typeOfExpenseMap: Map<Int, TypeOfExpense>, navController: NavController) {
    LazyColumn(Modifier.padding(3.dp)) {
        expenses.groupBy { it.getKey() }.forEach { (initial, expensesInSpecificDate) ->
            val calculateSumOfValue = {type: Type -> expensesInSpecificDate.filter { typeOfExpenseMap[it.typeOfExpenseId]!!.type == type }.map { it.price }.sum()}

            val incomeValue = calculateSumOfValue(Type.INCOME)
            val outgoValue = calculateSumOfValue(Type.OUTGO)

            stickyHeader {
                Row(
                    Modifier.fillMaxWidth().background(MaterialTheme.colors.background).padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(DateUtils.dateToStringWithMonthAndYear(date = expensesInSpecificDate.first().date), style = MaterialTheme.typography.subtitle1)

                    Row {
                        Text("-$outgoValue", style = MaterialTheme.typography.subtitle1, color = Color.Red)
                        Text("/", style = MaterialTheme.typography.subtitle1)
                        Text("$incomeValue", style = MaterialTheme.typography.subtitle1, color = Color.Green)
                    }
                }
            }

            items(expensesInSpecificDate) { expense ->
                ExpenseCard(
                    expense = expense,
                    typeOfExpense = typeOfExpenseMap[expense.typeOfExpenseId],
                    navController = navController
                )
            }
        }
    }
}