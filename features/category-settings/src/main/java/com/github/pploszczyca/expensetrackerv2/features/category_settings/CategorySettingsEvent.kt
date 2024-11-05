package com.github.pploszczyca.expensetrackerv2.features.category_settings

import com.github.pploszczyca.expensetrackerv2.domain.Category

sealed interface CategorySettingsEvent {
    data class NameChange(val value: String) : CategorySettingsEvent
    data class OpenFormDialog(val value: Category? = null) : CategorySettingsEvent
    data object CloseFormDialog : CategorySettingsEvent
    data class DialogFormSubmit(val name: String) : CategorySettingsEvent
    data class OpenDeleteDialog(val value: Category) : CategorySettingsEvent
    data object CloseDeleteDialog : CategorySettingsEvent
    data object DeleteDialogSubmit : CategorySettingsEvent
    data object OnBackButtonClicked : CategorySettingsEvent
}
