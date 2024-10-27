package com.github.pploszczyca.expensetrackerv2.navigation.contract

interface NavigationRouter {
    suspend fun goBack()
    suspend fun goToMain()
    suspend fun goToExpenseForm(expenseId: Int)
    suspend fun goToExpenseForm()
    suspend fun goToExpenseStatistics()
    suspend fun goToCategorySettings()
}