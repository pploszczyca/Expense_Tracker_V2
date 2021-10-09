package com.example.expensetrackerv2

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.providers.SampleDataProvider
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme

@Composable
fun ExpenseCard(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Column {
            Text(style = MaterialTheme.typography.caption, text = expense.date.toString())
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(style = MaterialTheme.typography.h5, text = expense.title)
                Text(style = MaterialTheme.typography.h5, text = expense.price.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerV2Theme {
        ExpenseCard(expense = SampleDataProvider.sampleExpenses()[0])
    }
}