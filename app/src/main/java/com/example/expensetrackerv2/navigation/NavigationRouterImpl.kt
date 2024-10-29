package com.example.expensetrackerv2.navigation

import androidx.navigation.NavHostController
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import kotlinx.coroutines.withContext
import com.github.pploszczyca.expensetrackerv2.domain.Id

internal class NavigationRouterImpl(
    private val navController: NavHostController,
    private val dispatcherProvider: DispatcherProvider,
) : NavigationRouter {
    override suspend fun goBack(): Unit = withContext(dispatcherProvider.main) {
        navController.navigateUp()
    }

    override suspend fun goToMain() = withContext(dispatcherProvider.main) {
        navController.navigate(Routes.Main.route)
    }

    override suspend fun goToExpenseForm(expenseId: Id) = withContext(dispatcherProvider.main) {
        navController.navigate(Routes.ExpenseForm.route + "?EXPENSE_ID=$expenseId")
    }

    override suspend fun goToExpenseForm() = withContext(dispatcherProvider.main) {
        navController.navigate(Routes.ExpenseForm.route)
    }

    override suspend fun goToExpenseStatistics() = withContext(dispatcherProvider.main) {
        navController.navigate(Routes.ExpenseStatistics.route)
    }

    override suspend fun goToCategorySettings() = withContext(dispatcherProvider.main) {
        navController.navigate(Routes.CategorySettings.route)
    }
}