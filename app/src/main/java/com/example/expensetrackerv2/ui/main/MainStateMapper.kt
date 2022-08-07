package com.example.expensetrackerv2.ui.main

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MainStateMapper @Inject constructor() {
    fun toViewState(state: MainViewModel.ViewModelState): MainViewModel.ViewState =
        with(state) {
            MainViewModel.ViewState(
                currentMonthYearKey = currentMonthYearKey,
                searchedTitle = searchedTitle,
                expenseToDelete = expenseToDelete,
                expensesWithItsType = expensesWithItsType.orEmpty(),
                isTopBarVisible = isTopBarVisible,
                isDeleteDialogVisible = isDeleteDialogVisible,
                clearButtonVisible = currentMonthYearKey != null,
                mainExpenseInformationVisible = isTopBarVisible.not()
            )
        }
}