package com.example.expensetrackerv2.ui.form.mappers

import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModel
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModelImpl

class AddEditFormStateMapper {
    fun toViewState(state: AddEditFormViewModelImpl.State): AddEditFormViewModel.ViewState {
        return AddEditFormViewModel.ViewState(
            title = state.title,
            price = state.price,
            typeOfExpenseRecords = state.typeOfExpenses?.map {
                AddEditFormViewModel.ViewState.TypeOfExpenseRecord(
                    id = it.id,
                    name = it.name,
                    selected = it.id == state.selectedTypeOfExpenseId
                )
            } ?: emptyList(),
            date = state.date,
            place = state.place,
            description = state.description,
            titleSuggestions = getSimilarValues(state.expensesTitles, state.title) ?: emptyList(),
            placeSuggestions = getSimilarValues(state.expensesTitles, state.place) ?: emptyList(),
            isAllDataLoaded = state.title.isNotEmpty() && state.price.isNotEmpty() && state.price.toDouble() >= 0.0 && state.selectedTypeOfExpenseId != -2,
            buttonTextId = when (state.isNewExpense) {
                true -> R.string.add
                false -> R.string.update
            }
        )
    }

    private fun getSimilarValues(values: List<String>?, valueToMatch: String) =
        values
            ?.distinct()
            ?.filter { it.contains(valueToMatch) }

    fun toExpenseWithItsType(state: AddEditFormViewModelImpl.State): ExpenseWithItsType {
        val selectedTypeOfExpense: TypeOfExpense =
            state.typeOfExpenses?.find { it.id == state.selectedTypeOfExpenseId } ?: TypeOfExpense()

        return ExpenseWithItsType(
            id = state.id,
            title = state.title,
            date = state.date,
            price = state.price.toDouble(),
            place = state.place,
            description = state.description,
            type = selectedTypeOfExpense.type,
            typeID = selectedTypeOfExpense.id,
            typeName = selectedTypeOfExpense.name
        )
    }
}
