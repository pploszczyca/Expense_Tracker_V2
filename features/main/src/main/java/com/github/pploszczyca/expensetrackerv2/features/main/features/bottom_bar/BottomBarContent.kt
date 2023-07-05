package com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar

import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.R

@Composable
fun BottomBarContent(
    viewModel: MainBottomBarViewModel,
    isClearButtonVisible: Boolean,
) {
    BottomAppBar(
        modifier = Modifier.height(55.dp)
    ) {
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