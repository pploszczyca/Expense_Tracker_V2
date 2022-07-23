package com.example.expensetrackerv2

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.ui.form.view.AddEditForm
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModel
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModelImpl
import com.example.expensetrackerv2.ui.main.MainComposable
import com.example.expensetrackerv2.ui.statistics.ExpensesStatistics
import com.example.expensetrackerv2.ui.type_of_expense_settings.TypeOfExpenseSettings

@Composable
fun NavHostComposable() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            MainComposable(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(
            Routes.ExpenseForm.route.plus("?EXPENSE_ID={EXPENSE_ID}"),
            arguments = listOf(navArgument("EXPENSE_ID") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val expensesFormViewModel: AddEditFormViewModel =
                hiltViewModel<AddEditFormViewModelImpl>()

            val id = backStackEntry.arguments?.getInt(
                "EXPENSE_ID"
            ) ?: ExpenseConstants.NEW_EXPENSE_ID

            expensesFormViewModel.onEvent(AddEditFormViewModel.Event.IdChange(id = id))

            AddEditForm(
                navController = navController,
                viewModel = expensesFormViewModel,
            )
        }
        composable(Routes.ExpenseStatistics.route) {
            ExpensesStatistics(
                navController = navController,
                expensesStatisticsViewModel = hiltViewModel()
            )
        }
        composable(Routes.TypeOfExpenseSettings.route) {
            TypeOfExpenseSettings(
                navController = navController,
                modelView = hiltViewModel()
            )
        }
    }
}
