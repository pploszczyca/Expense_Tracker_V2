package com.example.expensetrackerv2.ui.main

import android.net.Uri
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType

sealed interface MainEvent {
    data class MonthYearKeyChange(val value: ExpenseMonthYearKey?) : MainEvent
    data class SearchedTitleChange(val value: String) : MainEvent
    data class ExportToJsonButtonClick(val value: Uri?) : MainEvent
    data class ImportFromJsonButtonClick(val value: Uri?) : MainEvent
    data class DeleteButtonClick(val value: ExpenseWithItsType) : MainEvent
    object DismissDeleteButtonClick : MainEvent
    object ConfirmDeleteButtonClick : MainEvent
    object OnTopBarTrailingIconClick : MainEvent

    sealed interface BottomBar {
        object SearchButtonClick : MainEvent
        object ClearButtonClick : MainEvent
    }
}
