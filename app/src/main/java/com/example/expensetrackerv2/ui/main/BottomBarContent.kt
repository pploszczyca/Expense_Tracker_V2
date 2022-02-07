package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomBarContent(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    actualExpenseMonthYearKey: MutableState<ExpenseMonthYearKey?>,
    onSearchButtonClick: () -> Unit
) {
    BottomAppBar(cutoutShape = CircleShape) {
        IconButton(onClick = {
            coroutineScope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon))
        }

        Spacer(Modifier.weight(1f, true))

        if (actualExpenseMonthYearKey.value != null) {
            IconButton(onClick = {
                actualExpenseMonthYearKey.value = null
            }) {
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