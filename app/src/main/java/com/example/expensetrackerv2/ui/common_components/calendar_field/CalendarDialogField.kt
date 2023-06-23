package com.example.expensetrackerv2.ui.common_components.calendar_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.ui.form.ui.ExpenseFormTextField
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun CalendarDialogField(
    date: String,
    label: String = "",
    icon: ImageVector = Icons.Default.Today,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
    onDatePickerPick: (dateFromDialog: LocalDate) -> Unit,
) {
    val dialogState = rememberMaterialDialogState()

    ExpenseFormTextField(
        value = date,
        icon = icon,
        label = label,
        enabled = false,
        modifier = modifier
            .clickable { dialogState.show() }
    )

    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton(stringResource(id = R.string.ok))
        negativeButton(stringResource(id = R.string.cancel))
    }) {
        datepicker { onDatePickerPick(it) }
    }
}