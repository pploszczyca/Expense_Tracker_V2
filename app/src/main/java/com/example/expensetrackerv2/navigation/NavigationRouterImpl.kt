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

    override fun goToExpenseForm(expenseId: Int) {
        navController.navigate(Routes.ExpenseForm.route + "?EXPENSE_ID=$expenseId")
    }

    override fun goToExpenseForm() {
        navController.navigate(Routes.ExpenseForm.route)
    }

    override fun goToExpenseStatistics() {
        navController.navigate(Routes.ExpenseStatistics.route)
    }

    override fun goToCategorySettings() {
        navController.navigate(Routes.CategorySettings.route)
    }
}