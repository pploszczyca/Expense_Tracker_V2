package com.example.expensetrackerv2

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import com.example.expensetrackerv2.ui.form.ExpenseForm
import com.example.expensetrackerv2.ui.main.MainComposable
import com.example.expensetrackerv2.ui.statistics.ExpensesStatistics

@Composable
fun NavHostComposable(
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    typeOfExpenseRepository: TypeOfExpenseRepository
) {
    val navController = rememberNavController()

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
            ExpenseForm(
                navController = navController,
                expenseWithItsTypeRepository = expenseWithItsTypeRepository,
                expenseID = backStackEntry.arguments?.getInt("EXPENSE_ID"),
                typeOfExpenseRepository = typeOfExpenseRepository
            )
        }
        composable(Routes.ExpenseStatistics.route) {
            ExpensesStatistics(
                navController = navController,
                expenseWithItsTypeRepository = expenseWithItsTypeRepository
            )
        }
    }
}
