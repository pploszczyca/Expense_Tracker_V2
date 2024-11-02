package com.github.pploszczyca.expensetrackerv2.features.main

sealed interface MainEvent {
    data class SearchedTitleChange(val value: String) : MainEvent
    data object OnTopBarTrailingIconClick : MainEvent
    data object OnStatisticsItemClicked : MainEvent
    data object OnCategorySettingsItemClicked : MainEvent
    data object OnAddNewExpenseButtonClicked : MainEvent
}
