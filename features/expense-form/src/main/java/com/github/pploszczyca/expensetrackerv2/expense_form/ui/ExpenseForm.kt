package com.github.pploszczyca.expensetrackerv2.expense_form.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.auto_complite_text_field.AutoCompleteOutlinedTextField
import com.github.pploszczyca.expensetrackerv2.common_ui.calendar_field.CalendarDialogField
import com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field.ExpenseFormTextField
import com.github.pploszczyca.expensetrackerv2.common_ui.switcher.SwitchValue
import com.github.pploszczyca.expensetrackerv2.common_ui.switcher.Switcher
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.expense_form.view_model.ExpenseFormViewModel
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ExpenseForm(
    viewState: ExpenseFormViewModel.ViewState,
    onTitleChanged: (title: String) -> Unit = {},
    onPriceChanged: (price: String) -> Unit = {},
    onCategoryChanged: (categoryId: Id) -> Unit = {},
    onDateChanged: (date: ExpenseDate) -> Unit = {},
    onPlaceNameChanged: (placeName: String) -> Unit = {},
    onDescriptionChanged: (description: String) -> Unit = {},
    onSubmitButtonClicked: () -> Unit = {},
    onIncomeValueChanged: () -> Unit = {},
    onOutgoValueChanged: () -> Unit = {},
) {
    var hasAttemptedSubmit by rememberSaveable { mutableStateOf(false) }
    val isTitleInvalid = hasAttemptedSubmit && viewState.title.isBlank()
    val isPriceInvalid = hasAttemptedSubmit && viewState.price == Price.ZERO

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Switcher(
                value = when (viewState.type) {
                    Expense.Type.Income -> SwitchValue.On
                    Expense.Type.Outgo -> SwitchValue.Off
                },
                onText = "Income (+)",
                offText = "Outgo (-)",
                title = stringResource(id = R.string.expense_form_type),
                onValueChange = {
                    when (it) {
                        SwitchValue.On -> onIncomeValueChanged()
                        SwitchValue.Off -> onOutgoValueChanged()
                    }
                }
            )

            AutoCompleteOutlinedTextField(
                value = viewState.title,
                onValueChange = onTitleChanged,
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = viewState.previousTitles,
                isError = isTitleInvalid,
                supportingText = if (isTitleInvalid) stringResource(id = R.string.expense_form_title_error) else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = true,
                    showKeyboardOnFocus = true,
                ),
                focusOnLoad = viewState.shouldOpenKeyboard,
            )

            ExpenseFormTextField(
                value = viewState.price,
                onValueChange = onPriceChanged,
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                isError = isPriceInvalid,
                supportingText = if (isPriceInvalid) stringResource(id = R.string.expense_form_price_error) else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                )
            )

            CalendarDialogField(
                date = viewState.date.toString(),
                label = stringResource(id = R.string.expense_form_date),
                onDatePickerPick = {
                    onDateChanged(ExpenseDate.of(it))
                }
            )

            AutoCompleteOutlinedTextField(
                value = viewState.placeName.orEmpty(),
                onValueChange = onPlaceNameChanged,
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = viewState.previousPlaceNames,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = true,
                )
            )

            ExpenseFormTextField(
                value = viewState.description.orEmpty(),
                onValueChange = onDescriptionChanged,
                icon = Icons.AutoMirrored.Default.Message,
                label = stringResource(id = R.string.expense_form_description),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    autoCorrectEnabled = true,
                )
            )

            if (viewState.categories.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.expense_form_category),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    viewState.categories.forEach { category ->
                        FilterChip(
                            selected = category.isSelected,
                            onClick = { onCategoryChanged(category.id) },
                            label = { Text(text = category.name) },
                            leadingIcon = if (category.isSelected) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else null,
                        )
                    }
                }
            }
        }

        HorizontalDivider()
        Surface(tonalElevation = 2.dp) {
            Button(
                onClick = {
                    hasAttemptedSubmit = true
                    onSubmitButtonClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(id = viewState.submitButtonText),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseFormPreview() {
    val categories = listOf(
        ExpenseFormViewModel.ViewState.Category(id = Id.NO_ID, name = "Some Category"),
        ExpenseFormViewModel.ViewState.Category(id = Id.NO_ID, name = "Shopping"),
    )

    val viewState = ExpenseFormViewModel.ViewState(
        title = "Example title",
        price = Price.of("123.45"),
        chosenCategoryId = null,
        date = ExpenseDate.now(),
        placeName = "Biedronka",
        description = "Some description",
        previousTitles = emptyList(),
        previousPlaceNames = listOf("My Biedronka", "Super Biedronka"),
        categories = categories,
        submitButtonText = R.string.add
    )

    ExpenseForm(
        viewState = viewState,
    )
}
