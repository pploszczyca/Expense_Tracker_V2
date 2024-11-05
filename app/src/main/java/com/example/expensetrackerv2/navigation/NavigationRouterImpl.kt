package com.example.expensetrackerv2.navigation

import androidx.navigation.NavHostController
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class NavigationRouterImpl(
    private val navController: NavHostController,
    private val dispatcherProvider: DispatcherProvider,
) : NavigationRouter {
    override suspend fun goBack(): Unit = withContext(dispatcherProvider.main) {
        Timber.i("Navigating back")
        navController.navigateUp()
    }

    override suspend fun goToMain() = withContext(dispatcherProvider.main) {
        Timber.i("Navigating to main")
        navController.navigate(Routes.Main.route)
    }

    override suspend fun goToExpenseForm(expenseId: Id) = withContext(dispatcherProvider.main) {
        Timber.i("Navigating to expense form with expenseId: $expenseId")
        navController.navigate(Routes.ExpenseForm.route + "?EXPENSE_ID=$expenseId")
    }

    override suspend fun goToExpenseForm() = withContext(dispatcherProvider.main) {
        Timber.i("Navigating to expense form")
        navController.navigate(Routes.ExpenseForm.route)
    }

    override suspend fun goToExpenseStatistics() = withContext(dispatcherProvider.main) {
        Timber.i("Navigating to expense statistics")
        navController.navigate(Routes.ExpenseStatistics.route)
    }

    override suspend fun goToCategorySettings() = withContext(dispatcherProvider.main) {
        Timber.i("Navigating to category settings")
        navController.navigate(Routes.CategorySettings.route)
    }
}