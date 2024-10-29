package com.github.pploszczyca.expensetrackerv2.navigation.contract

import com.github.pploszczyca.expensetrackerv2.domain.Id

interface NavigationRouter {
    suspend fun goBack()
    suspend fun goToMain()
    suspend fun goToExpenseForm(expenseId: Id)
    suspend fun goToExpenseForm()
    suspend fun goToExpenseStatistics()
    suspend fun goToCategorySettings()
}