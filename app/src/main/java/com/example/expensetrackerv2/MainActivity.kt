package com.example.expensetrackerv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeDatabaseRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseDatabaseRepository
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val currentContext = LocalContext.current
                    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()
                    val expenseWithItsTypeDatabaseRepository =
                        ExpenseWithItsTypeDatabaseRepository(expenseDao)
                    val typeOfExpenseRepository = TypeOfExpenseDatabaseRepository(expenseDao)

                    NavHostComposable(
                        expenseWithItsTypeRepository = expenseWithItsTypeDatabaseRepository,
                        typeOfExpenseRepository = typeOfExpenseRepository
                    )
                }
            }
        }
    }
}
