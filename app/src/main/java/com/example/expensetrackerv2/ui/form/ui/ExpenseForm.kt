package com.example.expensetrackerv2.ui.form.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.ui.form.view_model.ExpenseFormViewModel
import java.time.LocalDate

@Composable
fun ExpenseForm(
    viewState: ExpenseFormViewModel.ViewState,
    onTitleChanged: (title: String) -> Unit,
    onPriceChanged: (price: String) -> Unit,
    onCategoryChanged: (categoryId: Int) -> Unit,
    onDateChanged: (date: LocalDate) -> Unit,
    onPlaceNameChanged: (placeName: String) -> Unit,
    onDescriptionChanged: (description: String) -> Unit,
    onSubmitButtonClicked: () -> Unit,
) {
    Column {
        AutoCompleteOutlinedTextField(
            value = viewState.title,
            onValueChange = onTitleChanged,
            icon = Icons.Default.Title,
            label = stringResource(id = R.string.expense_form_title),
            suggestionsInput = viewState.previousTitles
        )

        ExpenseFormTextField(
            value = viewState.price,
            onValueChange = onPriceChanged,
            icon = Icons.Default.AttachMoney,
            label = stringResource(id = R.string.expense_form_price),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        CalendarDialogField(
            date = viewState.date,
            label = stringResource(id = R.string.expense_form_date),
            onDatePickerPick = onDateChanged
        )

        AutoCompleteOutlinedTextField(
            value = viewState.placeName,
            onValueChange = onPlaceNameChanged,
            icon = Icons.Default.Place,
            label = stringResource(id = R.string.expense_form_place),
            suggestionsInput = viewState.previousPlaceNames
        )

        ExpenseFormTextField(
            value = viewState.description,
            onValueChange = onDescriptionChanged,
            icon = Icons.Default.Message,
            label = stringResource(id = R.string.expense_form_description),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            viewState.categories.forEach { category ->
                Row(verticalAlignment = CenterVertically) {
                    RadioButton(
                        selected = category.isSelected,
                        onClick = { onCategoryChanged(category.id) },
                    )
                    Text(text = category.name, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
        Button(onClick = onSubmitButtonClicked, modifier = Modifier.padding(20.dp)) {
            Text(text = stringResource(id = viewState.submitButtonText))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseFormPreview() {
    val categories = listOf(
        ExpenseFormViewModel.ViewState.Category(id = 0, name = "INCOME"),
        ExpenseFormViewModel.ViewState.Category(id = 1, name = "OUTGO"),
    )

    val viewState = ExpenseFormViewModel.ViewState(
        title = "Example title",
        price = "7.00",
        chosenCategoryId = 1,
        date = "22-03-2023",
        placeName = "Biedronka",
        description = "Some description",
        previousTitles = emptyList(),
        previousPlaceNames = listOf("My Biedronka", "Super Biedronka"),
        categories = categories,
        submitButtonText = R.string.add
    )

    ExpenseForm(
        viewState = viewState,
        onTitleChanged = {},
        onPriceChanged = {},
        onCategoryChanged = {},
        onDateChanged = {},
        onPlaceNameChanged = {},
        onDescriptionChanged = {},
        onSubmitButtonClicked = {},
    )
}
