package com.example.expensetrackerv2.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    suggestionsInput: List<String> = emptyList(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
) {
    var typedText by remember { mutableStateOf(value) }
    val suggestions = suggestionsInput.distinct()

    Column(modifier = Modifier.fillMaxWidth()) {
        ExpenseFormTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                typedText = it
            },
            icon = icon,
            label = label,
            keyboardOptions = keyboardOptions
        )

        DropdownMenu(
            expanded = (typedText != "" && suggestions.isNotEmpty() && suggestions.filter {
                it.contains(
                    typedText
                )
            }.isNotEmpty() && suggestions.filter { it == typedText }.size != 1),
            onDismissRequest = { },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.filter { it.contains(typedText) }.forEach { suggestedText ->
                DropdownMenuItem(
                    onClick = {
                        typedText = suggestedText
                        onValueChange(suggestedText)
                    }) {
                    Text(text = suggestedText)
                }
            }
        }
    }
}