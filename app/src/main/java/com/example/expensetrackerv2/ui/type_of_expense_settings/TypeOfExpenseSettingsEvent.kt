package com.example.expensetrackerv2.ui.type_of_expense_settings

import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.TypeOfExpense

sealed class TypeOfExpenseSettingsEvent {
    data class IdChange(val value: Int) : TypeOfExpenseSettingsEvent()
    data class NameChange(val value: String) : TypeOfExpenseSettingsEvent()
    data class TypeChange(val value: Type) : TypeOfExpenseSettingsEvent()
    data class OpenFormDialog(val value: TypeOfExpense = TypeOfExpense()) :
        TypeOfExpenseSettingsEvent()

    data class CloseFormDialog(val value: Any? = null) : TypeOfExpenseSettingsEvent()
    data class DialogFormSubmit(val value: TypeOfExpense) : TypeOfExpenseSettingsEvent()
    data class OpenDeleteDialog(val value: TypeOfExpense) : TypeOfExpenseSettingsEvent()
    data class CloseDeleteDialog(val value: Any? = null) : TypeOfExpenseSettingsEvent()
    data class DeleteDialogSubmit(val value: Any? = null) : TypeOfExpenseSettingsEvent()
}
