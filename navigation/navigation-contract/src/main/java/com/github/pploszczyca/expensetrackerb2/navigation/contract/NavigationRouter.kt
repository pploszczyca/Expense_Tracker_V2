package com.github.pploszczyca.expensetrackerb2.navigation.contract

interface NavigationRouter {
    fun goBack()

    fun goToMain()

    fun goToExpenseForm(expenseId: Int?)

    fun goToExpenseStatistics()

    fun goToCategorySettings()
}