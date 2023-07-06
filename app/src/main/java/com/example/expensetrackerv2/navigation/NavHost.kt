package com.example.expensetrackerv2.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.pploszczyca.expensetrackerv2.features.category_settings.CategorySettings
import com.github.pploszczyca.expensetrackerv2.expense_form.ui.ExpenseFormEntry
import com.github.pploszczyca.expensetrackerv2.expense_form.view_model.ExpenseFormViewModel
import com.github.pploszczyca.expensetrackerv2.expense_form.view_model.ExpenseFormViewModelImpl
import com.github.pploszczyca.expensetrackerv2.features.main.MainComposable
import com.github.pploszczyca.expensetrackerv2.expense_statistics.ui.ExpensesStatistics

@Composable
fun NavHostComposable(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            MainComposable(viewModel = hiltViewModel())
        }
        composable(
            Routes.ExpenseForm.route.plus("?EXPENSE_ID={EXPENSE_ID}"),
            arguments = listOf(navArgument("EXPENSE_ID") {
                type = NavType.IntType
                defaultValue = ExpenseFormViewModel.NO_EXPENSE_ID
            })
        ) { backStackEntry ->
            ExpenseFormEntry(viewModel = hiltViewModel<ExpenseFormViewModelImpl>(backStackEntry))
        }
        composable(Routes.ExpenseStatistics.route) {
            ExpensesStatistics(viewModel = hiltViewModel())
        }
        composable(Routes.CategorySettings.route) {
            CategorySettings(viewModel = hiltViewModel())
        }
    }
}
