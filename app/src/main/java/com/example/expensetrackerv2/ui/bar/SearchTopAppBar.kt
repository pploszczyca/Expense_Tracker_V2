package com.example.expensetrackerv2.ui.bar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchTopAppBar(onTrailingIconClick: () -> Unit, onValueChange: (String) -> Unit) {
    var value by remember {
        mutableStateOf("")
    }

    TopAppBar(backgroundColor = Color.Unspecified) {
        TextField(
            value = value,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Unspecified, backgroundColor = Color.Unspecified),
            singleLine = true,
            onValueChange = {
                value = it
                onValueChange(value)
            },
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
}

@Composable
@Preview
fun SearchTopAppBarPreview() {
    SearchTopAppBar({}) {}
}