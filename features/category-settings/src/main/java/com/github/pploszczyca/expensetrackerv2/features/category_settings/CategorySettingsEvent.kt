package com.github.pploszczyca.expensetrackerv2.features.category_settings

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Id

sealed interface CategorySettingsEvent {
    data class IdChange(val value: Id) : CategorySettingsEvent
    data class NameChange(val value: String) : CategorySettingsEvent
    data class OpenFormDialog(val value: Category = getNewCategory()) : CategorySettingsEvent
    object CloseFormDialog : CategorySettingsEvent
    data class DialogFormSubmit(val name: String) : CategorySettingsEvent
    data class OpenDeleteDialog(val value: Category) : CategorySettingsEvent
    object CloseDeleteDialog : CategorySettingsEvent
    object DeleteDialogSubmit : CategorySettingsEvent
    object OnBackButtonClicked : CategorySettingsEvent

    private companion object {
        fun getNewCategory(): Category = Category.new(
            name = "",
        )
    }
}
