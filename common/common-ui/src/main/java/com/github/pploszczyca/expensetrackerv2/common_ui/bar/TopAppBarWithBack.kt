package com.github.pploszczyca.expensetrackerv2.common_ui.bar

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBack(
    title: String,
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Unspecified,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarWithBackPreview() {
    TopAppBarWithBack(
        title = "Example title",
        onBackClicked = {},
    )
}