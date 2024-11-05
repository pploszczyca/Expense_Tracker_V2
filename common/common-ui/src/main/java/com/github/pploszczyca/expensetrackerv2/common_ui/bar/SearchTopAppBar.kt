package com.github.pploszczyca.expensetrackerv2.common_ui.bar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    searchedValue: String = "",
    onTrailingIconClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Unspecified,
    ),
        title = {
            TextField(
                value = searchedValue,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Unspecified,
                ),
                singleLine = true,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxSize(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { onTrailingIconClick() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    )
}

@Composable
@Preview
fun SearchTopAppBarPreview() {
    SearchTopAppBar(onTrailingIconClick = {}) {}
}