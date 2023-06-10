package com.example.expensetrackerv2.ui.category_settings

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.CategoryType

sealed class CategorySettingsEvent {
    data class IdChange(val value: Int) : CategorySettingsEvent()
    data class NameChange(val value: String) : CategorySettingsEvent()
    data class TypeChange(val value: CategoryType) : CategorySettingsEvent()
    data class OpenFormDialog(val value: CategoryEntity = CategoryEntity()) :
        CategorySettingsEvent()

    data class CloseFormDialog(val value: Any? = null) : CategorySettingsEvent()
    data class DialogFormSubmit(val value: CategoryEntity) : CategorySettingsEvent()
    data class OpenDeleteDialog(val value: CategoryEntity) : CategorySettingsEvent()
    data class CloseDeleteDialog(val value: Any? = null) : CategorySettingsEvent()
    data class DeleteDialogSubmit(val value: Any? = null) : CategorySettingsEvent()
}
