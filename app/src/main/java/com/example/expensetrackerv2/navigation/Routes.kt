package com.example.expensetrackerv2.navigation

sealed class Routes(val route: String) {
    data object Main : Routes("main/")
    data object ExpenseForm : Routes("expenseForm/")
    data object ExpenseStatistics : Routes("expenseStatistics/")
    data object CategorySettings : Routes("categorySettings/")
}
