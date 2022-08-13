package com.example.expensetrackerv2.ui.main.features.filter_dialog

import com.example.expensetrackerv2.database.models.MonthYearKey

sealed interface MainFilterDialogEvent {
    data class OptionSelected(val key: MonthYearKey)
}
