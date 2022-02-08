package com.example.expensetrackerv2

sealed class Routes(val route: String) {
    object Main : Routes("main/")
    object ExpenseForm : Routes("expenseForm/")
    object ExpenseStatistics : Routes("expenseStatistics/")
    object TypeOfExpenseSettings : Routes("typeOfExpenseSettings/")
}
