package com.example.expensetrackerv2.ui.main.features.bottom_bar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R

@Composable
fun BottomBarContent(
    onMenuButtonClick: () -> Unit,
    isClearButtonVisible: Boolean,
    onClearButtonClick: () -> Unit,
    onSearchButtonClick: () -> Unit
) {
    BottomAppBar(cutoutShape = CircleShape) {
        IconButton(onClick = onMenuButtonClick) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon))
        }

        Spacer(Modifier.weight(1f, true))

        if (isClearButtonVisible) {
            IconButton(onClick = onClearButtonClick) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.reset_icon)
                )
            }
        }

        IconButton(onClick = { onSearchButtonClick() }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon)
            )
        }
    }
}