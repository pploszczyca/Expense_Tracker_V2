package com.github.pploszczyca.expensetrackerv2.navigation.contract

interface NavigationRouter {
    fun goBack()

    fun goToMain()

    fun goToExpenseForm(expenseId: Int)

    fun goToExpenseForm()

    fun goToExpenseStatistics()

    fun goToCategorySettings()
}