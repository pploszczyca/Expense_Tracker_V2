package com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar

import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.features.main.R

@Composable
fun BottomBarContent(
    viewModel: MainBottomBarViewModel,
    floatingActionButton: @Composable (() -> Unit)? = null,
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.MenuButtonClick) }) {
                Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon))
            }

            IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.SearchButtonClick) }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            }
        },
        floatingActionButton = floatingActionButton,
    )
}