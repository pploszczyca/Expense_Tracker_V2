package com.example.expensetrackerv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import androidx.compose.foundation.lazy.items
import com.example.expensetrackerv2.providers.SampleDataProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainComposable(SampleDataProvider.sampleExpenses())
                }
            }
        }
    }
}

@Composable
fun ExpenesList(expenses: List<Expense>) {
    LazyColumn() {
        items(expenses) { expense ->
            ExpenseCard(expense = expense)
        }
    }
}

@Composable
fun MainComposable(expenses: List<Expense>) {
    ExpenesList(expenses = expenses)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerV2Theme {
        MainComposable(SampleDataProvider.sampleExpenses())
    }
}