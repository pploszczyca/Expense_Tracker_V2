package com.example.expensetrackerv2.ui.main

import android.net.Uri
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType

sealed class MainEvent {
    data class MonthYearKeyChange(val value: ExpenseMonthYearKey?) : MainEvent()
    data class SearchedTitleChange(val value: String) : MainEvent()
    data class TopBarVisibilityChange(val value: Boolean) : MainEvent()
    data class ExportToJsonButtonClick(val value: Uri?) : MainEvent()
    data class ImportFromJsonButtonClick(val value: Uri?) : MainEvent()
    data class DeleteButtonClick(val value: ExpenseWithItsType) : MainEvent()
    data class DismissDeleteButtonClick(val value: Any? = null) : MainEvent()
    data class ConfirmDeleteButtonClick(val value: Any? = null) : MainEvent()
}
