package com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseFormTextField(
    value: Any,
    onValueChange: (String) -> Unit = {},
    icon: ImageVector,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
    enabled: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null
            )
        },
        enabled = enabled,
        modifier = modifier,
    )
}