package com.github.pploszczyca.expensetrackerv2.common_ui.calendar_field

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.pploszczyca.expensetrackerv2.common.common_ui.R
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toLocalDate
import com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field.ExpenseFormTextField
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialogField(
    date: String,
    modifier: Modifier = Modifier,
    label: String = "",
    icon: ImageVector = Icons.Default.Today,
    onDatePickerPick: (dateFromDialog: LocalDate) -> Unit = {},
) {
    val datePickerState = rememberDatePickerState()
    var showModal by remember { mutableStateOf(false) }

    ExpenseFormTextField(
        value = date,
        icon = icon,
        label = label,
        enabled = false,
        modifier = modifier
            .clickable { showModal = true },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    )

    if (showModal) {
        DatePickerDialog(
            onDismissRequest = { showModal = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis
                        .toLocalDate()
                        ?.let { onDatePickerPick(it) }
                    showModal = false
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showModal = false
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun CalendarDialogFieldPreview() {
    CalendarDialogField(
        date = "08.07.2023",
    )
}