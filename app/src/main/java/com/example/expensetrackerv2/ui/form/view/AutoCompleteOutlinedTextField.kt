package com.example.expensetrackerv2.ui.form.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun AutoCompleteOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    label: String = "",
    suggestions: List<String> = emptyList(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AddEditFormTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            icon = icon,
            label = label,
            keyboardOptions = keyboardOptions
        )

        DropdownMenu(
            expanded = (suggestions.isNotEmpty()),
            onDismissRequest = {},
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { suggestedText ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(suggestedText)
                    }) {
                    Text(text = suggestedText)
                }
            }
        }
    }
}