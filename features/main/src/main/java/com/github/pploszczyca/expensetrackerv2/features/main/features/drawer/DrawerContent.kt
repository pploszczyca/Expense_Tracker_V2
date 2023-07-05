package com.github.pploszczyca.expensetrackerv2.features.main.features.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.R

@Composable
fun DrawerContent(
    onStatisticsItemClicked: () -> Unit = {},
    onCategorySettingsItemClicked: () -> Unit = {},
) {
    ModalDrawerSheet {
        Text(
            stringResource(id = R.string.drawer_options_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            onClick = onStatisticsItemClicked,
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.expense_statistics_app_bar_title)) },
            selected = false
        )

        NavigationDrawerItem(
            onClick = onCategorySettingsItemClicked,
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.drawer_type_of_expense_settings)) },
            selected = false,
        )
    }
}
