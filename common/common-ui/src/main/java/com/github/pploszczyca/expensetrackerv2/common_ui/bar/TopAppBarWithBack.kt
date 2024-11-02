package com.github.pploszczyca.expensetrackerv2.common_ui.bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBack(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            TopAppBarAction(
                iconImage = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description",
                onActionClicked = onBackClicked,
            )
        },
        actions = actions,
    )
}

@Composable
fun TopAppBarAction(
    iconImage: ImageVector,
    contentDescription: String,
    onActionClicked: () -> Unit,
) {
    IconButton(onClick = onActionClicked) {
        Icon(
            imageVector = iconImage,
            contentDescription = contentDescription
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarWithBackPreview() {
    TopAppBarWithBack(
        title = "Example title",
        onBackClicked = {},
    )
}