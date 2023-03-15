package com.example.expensetrackerv2.ui.form

import com.example.expensetrackerv2.models.CategoryEntity

sealed class AddEditFormEvent {
    data class IdChange(val value: Int) : AddEditFormEvent()
    data class TitleChange(val value: String) : AddEditFormEvent()
    data class PriceChange(val value: String) : AddEditFormEvent()
    data class TypeOfAddEditChange(val value: CategoryEntity) : AddEditFormEvent()
    data class DateChange(val value: String) : AddEditFormEvent()
    data class PlaceChange(val value: String) : AddEditFormEvent()
    data class DescriptionChange(val value: String) : AddEditFormEvent()
}
