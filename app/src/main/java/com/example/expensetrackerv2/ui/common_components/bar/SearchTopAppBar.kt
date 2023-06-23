package com.example.expensetrackerv2.ui.common_components.bar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
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
    TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = Color.Unspecified,
    ),
        title = {
            TextField(
                value = searchedValue,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Unspecified,
                    containerColor = Color.Unspecified
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