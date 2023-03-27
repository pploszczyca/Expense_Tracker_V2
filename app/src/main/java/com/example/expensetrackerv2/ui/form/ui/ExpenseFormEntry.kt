package com.example.expensetrackerv2.ui.form.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.expensetrackerv2.ui.form.view_model.ExpenseFormViewModel

@Composable
fun ExpenseFormEntry(
    viewModel: ExpenseFormViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    ExpenseForm(
        viewState = viewState,
        onTitleChanged = viewModel::onTitleChanged,
        onPriceChanged = viewModel::onPriceChanged,
        onCategoryChanged = viewModel::onCategoryChanged,
        onDateChanged = viewModel::onDateChanged,
        onPlaceNameChanged = viewModel::onPlaceNameChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onSubmitButtonClicked = viewModel::onSubmitButtonClicked,
    )
}