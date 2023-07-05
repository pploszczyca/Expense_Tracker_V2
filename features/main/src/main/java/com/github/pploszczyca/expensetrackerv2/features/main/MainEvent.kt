package com.github.pploszczyca.expensetrackerv2.features.main

import com.github.pploszczyca.expensetrackerv2.domain.Expense

sealed interface MainEvent {
    data class SearchedTitleChange(val value: String) : MainEvent
    data class DeleteButtonClick(val value: Expense) : MainEvent
    object DismissDeleteButtonClick : MainEvent
    object ConfirmDeleteButtonClick : MainEvent
    object OnTopBarTrailingIconClick : MainEvent
    object OnStatisticsItemClicked : MainEvent
    object OnCategorySettingsItemClicked : MainEvent
    object OnAddNewExpenseButtonClicked : MainEvent
}