package com.github.pploszczyca.expensetrackerv2.common_ui.auto_complite_text_field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.filterIf
import com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field.ExpenseFormTextField

@Composable
fun AutoCompleteOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    label: String = "",
    suggestionsInput: List<String> = emptyList(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
) {
    var shouldShowDropdown by remember { mutableStateOf(false) }
    val suggestions = suggestionsInput.filterIf(shouldShowDropdown && value.isNotEmpty()) {
        it.contains(value)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExpenseFormTextField(
            value = value,
            onValueChange = onValueChange,
            icon = icon,
            label = label,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .onFocusChanged {
                    shouldShowDropdown = it.isFocused
                }
        )

        DropdownMenu(
            expanded = (shouldShowDropdown && value.isNotEmpty() && suggestions.isNotEmpty()),
            onDismissRequest = { },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { suggestedText ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(suggestedText)
                        shouldShowDropdown = false
                    },
                    text = { Text(text = suggestedText) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AutoCompleteOutlinedTextFieldPreview() {
    AutoCompleteOutlinedTextField(
        value = "My Title",
        onValueChange = {},
        icon = Icons.Default.Title,
        label = "Title",
        suggestionsInput = listOf("Test")
    )
}