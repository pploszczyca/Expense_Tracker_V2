package com.example.expensetrackerv2.ui.main.features.bottom_bar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R

@Composable
fun BottomBarContent(
    viewModel: MainBottomBarViewModel,
    isClearButtonVisible: Boolean,
) {
    BottomAppBar(cutoutShape = CircleShape) {
        IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.MenuButtonClick) }) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon))
        }

        IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.SearchButtonClick) }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon)
            )
        }

        IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.FilterButtonClick) }) {
            Icon(
                Icons.Filled.FilterList,
                contentDescription = stringResource(id = R.string.filter_icon)
            )
        }

        if (isClearButtonVisible) {
            IconButton(onClick = { viewModel.onEvent(MainBottomBarEvent.ClearButtonClick) }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.reset_icon)
                )
            }
        }
    }
}