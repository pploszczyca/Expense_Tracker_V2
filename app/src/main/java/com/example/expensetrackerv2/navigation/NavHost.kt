package com.example.expensetrackerv2.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerv2.models.ExpenseConstants
import com.example.expensetrackerv2.navigation.Routes
import com.example.expensetrackerv2.ui.legacy_form.AddEditForm
import com.example.expensetrackerv2.ui.legacy_form.LegacyAddEditFormEvent
import com.example.expensetrackerv2.ui.legacy_form.LegacyAddEditFormViewModel
import com.example.expensetrackerv2.ui.main.MainComposable
import com.example.expensetrackerv2.ui.statistics.ExpensesStatistics
import com.example.expensetrackerv2.ui.category_settings.CategorySettings
import com.example.expensetrackerv2.ui.form.ui.ExpenseFormEntry
import com.example.expensetrackerv2.ui.form.view_model.ExpenseFormViewModel
import com.example.expensetrackerv2.ui.form.view_model.ExpenseFormViewModelImpl

@Composable
fun NavHostComposable() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            MainComposable(navController = navController, viewModel = hiltViewModel())
        }
        composable(
            Routes.ExpenseForm.route.plus("?EXPENSE_ID={EXPENSE_ID}"),
            arguments = listOf(navArgument("EXPENSE_ID") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            ExpenseFormEntry(viewModel = hiltViewModel<ExpenseFormViewModelImpl>(backStackEntry))
        }
        composable(Routes.ExpenseStatistics.route) {
            ExpensesStatistics(
                navController = navController,
                expensesStatisticsViewModel = hiltViewModel()
            )
        }
        composable(Routes.TypeOfExpenseSettings.route) {
            CategorySettings(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
    }
}
