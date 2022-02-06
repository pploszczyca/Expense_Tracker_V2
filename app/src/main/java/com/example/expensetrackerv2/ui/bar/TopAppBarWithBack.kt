package com.example.expensetrackerv2.ui.bar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun TopAppBarWithBack(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
            }
        }, backgroundColor = Color.Unspecified
    )
}