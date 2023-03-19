package com.example.expensetrackerv2.ui.legacy_form

import com.example.expensetrackerv2.models.CategoryEntity

sealed class LegacyAddEditFormEvent {
    data class IdChange(val value: Int) : LegacyAddEditFormEvent()
    data class TitleChange(val value: String) : LegacyAddEditFormEvent()
    data class PriceChange(val value: String) : LegacyAddEditFormEvent()
    data class TypeOfAddEditChange(val value: CategoryEntity) : LegacyAddEditFormEvent()
    data class DateChange(val value: String) : LegacyAddEditFormEvent()
    data class PlaceChange(val value: String) : LegacyAddEditFormEvent()
    data class DescriptionChange(val value: String) : LegacyAddEditFormEvent()
}
