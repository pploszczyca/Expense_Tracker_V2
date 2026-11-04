package com.github.pploszczyca.expensetrackerv2.expense_form.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.expense_form.view_model.ExpenseFormViewModel
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import java.time.LocalDate

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

    val todayDate = ExpenseDate.now()
    val yesterdayDate = ExpenseDate.of(LocalDate.now().minusDays(1))
    val amountColor = if (viewState.type == Expense.Type.Outgo) ExpenseColor else IncomeColor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Hero Card: Transaction Type + Prominent Amount
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Switcher(
                        value = when (viewState.type) {
                            Expense.Type.Income -> SwitchValue.On
                            Expense.Type.Outgo -> SwitchValue.Off
                        },
                        onText = "Income (+)",
                        offText = "Outgo (-)",
                        title = null,
                        onValueChange = {
                            when (it) {
                                SwitchValue.On -> onIncomeValueChanged()
                                SwitchValue.Off -> onOutgoValueChanged()
                            }
                        }
                    )

                    ExpenseFormTextField(
                        value = viewState.price,
                        onValueChange = onPriceChanged,
                        icon = Icons.Default.AttachMoney,
                        label = stringResource(id = R.string.expense_form_price),
                        isError = isPriceInvalid,
                        supportingText = if (isPriceInvalid) stringResource(id = R.string.expense_form_price_error) else null,
                        textStyle = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = amountColor,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        )
                    )
                }
            }

            // 2. Main Info Card: Title & Category Chips
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
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

                    if (viewState.categories.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(id = R.string.expense_form_category),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
                        )
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
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
            }

            // 3. Details Card: Date (with Today/Yesterday quick chips), Place, Notes
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.expense_form_details),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = viewState.date == todayDate,
                                onClick = { onDateChanged(todayDate) },
                                label = { Text(stringResource(id = R.string.date_today)) }
                            )
                            FilterChip(
                                selected = viewState.date == yesterdayDate,
                                onClick = { onDateChanged(yesterdayDate) },
                                label = { Text(stringResource(id = R.string.date_yesterday)) }
                            )
                        }
                    }

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
                        singleLine = false,
                        minLines = 2,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                            autoCorrectEnabled = true,
                        )
                    )
                }
            }
        }

        HorizontalDivider()
        Surface(tonalElevation = 3.dp) {
            Button(
                onClick = {
                    hasAttemptedSubmit = true
                    onSubmitButtonClicked()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(54.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = viewState.submitButtonText),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
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
