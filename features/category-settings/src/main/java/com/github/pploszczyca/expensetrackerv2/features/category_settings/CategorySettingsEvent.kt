package com.github.pploszczyca.expensetrackerv2.features.category_settings

import com.github.pploszczyca.expensetrackerv2.domain.Category

sealed interface CategorySettingsEvent {
    data class NameChange(val value: String) : CategorySettingsEvent
    data class OpenFormDialog(val value: Category? = null) : CategorySettingsEvent
    object CloseFormDialog : CategorySettingsEvent
    data class DialogFormSubmit(val name: String) : CategorySettingsEvent
    data class OpenDeleteDialog(val value: Category) : CategorySettingsEvent
    object CloseDeleteDialog : CategorySettingsEvent
    object DeleteDialogSubmit : CategorySettingsEvent
    object OnBackButtonClicked : CategorySettingsEvent
}
