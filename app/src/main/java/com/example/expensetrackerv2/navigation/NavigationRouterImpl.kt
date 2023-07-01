package com.example.expensetrackerv2.navigation

import androidx.navigation.NavHostController
import com.github.pploszczyca.expensetrackerb2.navigation.contract.NavigationRouter

internal class NavigationRouterImpl(
    private val navController: NavHostController,
) : NavigationRouter {
    override fun goBack() {
        navController.navigateUp()
    }

    override fun goToMain() {
        navController.navigate(Routes.Main.route)
    }

    override fun goToExpenseForm(expenseId: Int?) {
        val route = Routes.ExpenseForm.route + (expenseId?.let { "?EXPENSE_ID=$it" }.orEmpty())

        navController.navigate(route)
    }

    override fun goToExpenseStatistics() {
        navController.navigate(Routes.ExpenseStatistics.route)
    }

    override fun goToCategorySettings() {
        navController.navigate(Routes.CategorySettings.route)
    }
}