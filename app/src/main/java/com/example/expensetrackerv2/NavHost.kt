package com.example.expensetrackerv2

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import com.example.expensetrackerv2.ui.form.AddEditForm
import com.example.expensetrackerv2.ui.form.AddEditFormEvent
import com.example.expensetrackerv2.ui.form.AddEditFormViewModel
import com.example.expensetrackerv2.ui.main.MainComposable
import com.example.expensetrackerv2.ui.statistics.ExpensesStatistics
import com.example.expensetrackerv2.ui.statistics.ExpensesStatisticsViewModel
import com.example.expensetrackerv2.ui.type_of_expense_settings.TypeOfExpenseSettings

@Composable
fun NavHostComposable(
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    typeOfExpenseRepository: TypeOfExpenseRepository
) {
    val navController = rememberNavController()
    val expensesStatisticsViewModel: ExpensesStatisticsViewModel = viewModel()
    val expensesFormViewModel: AddEditFormViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            MainComposable(
                navController = navController,
                expenseWithItsTypeRepository = expenseWithItsTypeRepository
            )
        }
        composable(
            Routes.ExpenseForm.route.plus("?EXPENSE_ID={EXPENSE_ID}"),
            arguments = listOf(navArgument("EXPENSE_ID") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            expensesFormViewModel.onEvent(
                AddEditFormEvent.IdChange(
                    backStackEntry.arguments?.getInt(
                        "EXPENSE_ID"
                    ) ?: ExpenseConstants.NEW_EXPENSE_ID
                )
            )

            AddEditForm(
                navController = navController,
                expenseID = backStackEntry.arguments?.getInt("EXPENSE_ID"),
                addEditFormViewModel = expensesFormViewModel
            )
        }
        composable(Routes.ExpenseStatistics.route) {
            ExpensesStatistics(
                navController = navController,
                expensesStatisticsViewModel = expensesStatisticsViewModel
            )
        }
        composable(Routes.TypeOfExpenseSettings.route) {
            TypeOfExpenseSettings(
                typeOfExpenseRepository = typeOfExpenseRepository,
                navController = navController
            )
        }
    }
}
