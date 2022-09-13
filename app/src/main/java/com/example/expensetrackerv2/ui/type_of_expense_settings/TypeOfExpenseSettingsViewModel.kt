package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.TypeOfExpenseConstants
import com.example.expensetrackerv2.use_cases.type_of_expense.DeleteTypeOfExpense
import com.example.expensetrackerv2.use_cases.type_of_expense.GetTypesOfExpense
import com.example.expensetrackerv2.use_cases.type_of_expense.InsertTypeOfExpense
import com.example.expensetrackerv2.use_cases.type_of_expense.UpdateTypeOfExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TypeOfExpenseSettingsViewModel @Inject constructor(
    private val getTypesOfExpense: GetTypesOfExpense,
    private val insertTypeOfExpense: InsertTypeOfExpense,
    private val updateTypeOfExpense: UpdateTypeOfExpense,
    private val deleteTypeOfExpense: DeleteTypeOfExpense
) : ViewModel() {
    val typesOfExpense: Flow<List<TypeOfExpense>> = getTypesOfExpense()

    private val _id = mutableStateOf(TypeOfExpenseConstants.NEW_TYPE_OF_EXPENSE_ID)
    val id: State<Int> = _id

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _type = mutableStateOf(Type.INCOME)
    val type: State<Type> = _type

    private val _isDialogFormVisible = mutableStateOf(false)
    val isDialogFormVisible: State<Boolean> = _isDialogFormVisible

    private val _isDeleteDialogFormVisible = mutableStateOf(false)
    val isDeleteDialogFormVisible: State<Boolean> = _isDeleteDialogFormVisible

    fun onEvent(event: TypeOfExpenseSettingsEvent) {
        when (event) {
            is TypeOfExpenseSettingsEvent.IdChange -> _id.value = event.value
            is TypeOfExpenseSettingsEvent.NameChange -> _name.value = event.value
            is TypeOfExpenseSettingsEvent.TypeChange -> _type.value = event.value
            is TypeOfExpenseSettingsEvent.CloseDeleteDialog -> closeDialog(
                _isDeleteDialogFormVisible
            )
            is TypeOfExpenseSettingsEvent.CloseFormDialog -> closeDialog(_isDialogFormVisible)
            is TypeOfExpenseSettingsEvent.OpenDeleteDialog -> {
                setIdNameAndType(event.value)
                openDialog(_isDeleteDialogFormVisible)
            }
            is TypeOfExpenseSettingsEvent.OpenFormDialog -> {
                setIdNameAndType(event.value)
                openDialog(_isDialogFormVisible)
            }
            is TypeOfExpenseSettingsEvent.DialogFormSubmit -> {
                insertOrUpdate(event.value)
                onEvent(TypeOfExpenseSettingsEvent.CloseFormDialog())
            }
            is TypeOfExpenseSettingsEvent.DeleteDialogSubmit -> {
                delete(makeTypeOfExpenseFromState())
                onEvent(TypeOfExpenseSettingsEvent.CloseDeleteDialog())
            }
        }
    }

    fun isThisNewTypeOfExpense(): Boolean =
        id.value == TypeOfExpenseConstants.NEW_TYPE_OF_EXPENSE_ID

    private fun openDialog(state: MutableState<Boolean>) {
        state.value = true
    }

    private fun closeDialog(state: MutableState<Boolean>) {
        state.value = false
    }

    private fun setIdNameAndType(typeOfExpense: TypeOfExpense) {
        _id.value = typeOfExpense.id
        _name.value = typeOfExpense.name
        _type.value = typeOfExpense.type
    }

    private fun insertOrUpdate(typeOfExpense: TypeOfExpense) {
        viewModelScope.launch {
            if (isThisNewTypeOfExpense()) {
                insertTypeOfExpense(typeOfExpense)
            } else {
                updateTypeOfExpense(typeOfExpense)
            }
        }
    }

    private fun makeTypeOfExpenseFromState() =
        TypeOfExpense(id = id.value, name = name.value, type = type.value)

    private fun delete(typeOfExpense: TypeOfExpense) {
        viewModelScope.launch {
            deleteTypeOfExpense(typeOfExpense)
        }
    }
}