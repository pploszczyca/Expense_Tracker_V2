package com.example.expensetrackerv2

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.ui.form.AddEditForm
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormEvent
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModelImpl
import com.example.expensetrackerv2.ui.main.MainComposable
import com.example.expensetrackerv2.ui.main.MainViewModel
import com.example.expensetrackerv2.ui.statistics.ExpensesStatistics
import com.example.expensetrackerv2.ui.statistics.ExpensesStatisticsViewModel
import com.example.expensetrackerv2.ui.type_of_expense_settings.TypeOfExpenseSettings
import com.example.expensetrackerv2.ui.type_of_expense_settings.TypeOfExpenseSettingsModelView

@Composable
fun NavHostComposable() {
    val navController = rememberNavController()
    val expensesStatisticsViewModel: ExpensesStatisticsViewModel = viewModel()
    val expensesFormViewModel: AddEditFormViewModelImpl = viewModel()
    val typeOfExpenseSettingsModelView: TypeOfExpenseSettingsModelView = viewModel()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            MainComposable(navController = navController, viewModel = mainViewModel)
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
                viewModel = expensesFormViewModel
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
                navController = navController,
                modelView = typeOfExpenseSettingsModelView
            )
        }
    }
}
