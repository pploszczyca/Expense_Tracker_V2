package com.example.expensetrackerv2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import com.example.expensetrackerv2.ui.statistics.ExpensesStatisticsViewModel
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var expenseWithItsTypeRepository: ExpenseWithItsTypeRepository

    @Inject
    lateinit var typeOfExpenseRepository: TypeOfExpenseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavHostComposable(
                        expenseWithItsTypeRepository = expenseWithItsTypeRepository,
                        typeOfExpenseRepository = typeOfExpenseRepository
                    )
                }
            }
        }
    }
}
