package com.example.expensetrackerv2.ui.category_settings

import com.github.pploszczyca.expensetrackerv2.domain.Category

sealed class CategorySettingsEvent {
    data class IdChange(val value: Int) : CategorySettingsEvent()
    data class NameChange(val value: String) : CategorySettingsEvent()
    data class TypeChange(val value: Category.Type) : CategorySettingsEvent()
    data class OpenFormDialog(val value: Category = getNewCategory()) :
        CategorySettingsEvent()

    data class CloseFormDialog(val value: Any? = null) : CategorySettingsEvent()
    data class DialogFormSubmit(val value: Category) : CategorySettingsEvent()
    data class OpenDeleteDialog(val value: Category) : CategorySettingsEvent()
    data class CloseDeleteDialog(val value: Any? = null) : CategorySettingsEvent()
    data class DeleteDialogSubmit(val value: Any? = null) : CategorySettingsEvent()

    private companion object {
        fun getNewCategory(): Category = Category(
            name = "",
            type = Category.Type.INCOME
        )
    }
}
