package com.example.expensetrackerv2

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Type
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.providers.SampleDataProvider
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils

@Composable
fun ExpenseCard(expense: Expense, typeOfExpense: TypeOfExpense?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(style = MaterialTheme.typography.caption, text = DateUtils.toOnlyDateString(expense.date))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(style = MaterialTheme.typography.h5, text = expense.title)
                Text(style = MaterialTheme.typography.h5, text = (expense.price * typeOfExpense!!.type.multiplier).toString(), color = if(typeOfExpense.type == Type.OUTGO) Color.Red else Color.Green)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerV2Theme {
        ExpenseCard(expense = Expense(title = "Zakupy w Biedronce", price = 50.0, description = "Opis", place = "Wadowice"), typeOfExpense = TypeOfExpense(name = "Zakupy", type = Type.OUTGO))
    }
}