package com.example.expensetrackerv2.ui.main

import android.net.Uri
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory

sealed interface MainEvent {
    data class SearchedTitleChange(val value: String) : MainEvent
    data class ExportToJsonButtonClick(val value: Uri?) : MainEvent
    data class ImportFromJsonButtonClick(val value: Uri?) : MainEvent
    data class DeleteButtonClick(val value: ExpenseWithCategory) : MainEvent
    object DismissDeleteButtonClick : MainEvent
    object ConfirmDeleteButtonClick : MainEvent
    object OnTopBarTrailingIconClick : MainEvent
}
