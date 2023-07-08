package com.github.pploszczyca.expensetrackerv2.common_ui.calendar_field

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.pploszczyca.expensetrackerv2.common.common_ui.R
import com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field.ExpenseFormTextField
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun CalendarDialogField(
    date: String,
    modifier: Modifier = Modifier,
    label: String = "",
    icon: ImageVector = Icons.Default.Today,
    onDatePickerPick: (dateFromDialog: LocalDate) -> Unit = {},
) {
    val dialogState = rememberMaterialDialogState()

    ExpenseFormTextField(
        value = date,
        icon = icon,
        label = label,
        enabled = false,
        modifier = modifier
            .clickable { dialogState.show() },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    )

    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton(stringResource(id = R.string.ok))
        negativeButton(stringResource(id = R.string.cancel))
    }) {
        datepicker { onDatePickerPick(it) }
    }
}

@Preview
@Composable
fun CalendarDialogFieldPreview() {
    CalendarDialogField(
        date = "08.07.2023",
    )
}