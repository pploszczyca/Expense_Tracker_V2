package com.example.expensetrackerv2.ui.main.features.drawer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.navigation.Routes
import kotlinx.coroutines.Job

@Composable
fun DrawerContent(
    onExportToJsonClick: (Uri?) -> Unit,
    onImportFromJsonClick: (Uri?) -> Unit,
    closeDrawer: () -> Job,
    navController: NavController,
) {
    val exportJsonFileName = stringResource(id = R.string.drawer_months_title)

    val exportToJsonLauncher =
        rememberLauncherForActivityResult(CreateDocument("todo/todo")) {
            onExportToJsonClick(it)
            closeDrawer()
        }

    val importFromJsonLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            onImportFromJsonClick(it)
            closeDrawer()
        }

    ModalDrawerSheet {
        Text(
            stringResource(id = R.string.drawer_options_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            onClick = {
                navController.navigate(Routes.ExpenseStatistics.route)
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.drawer_statistics)) },
            selected = false
        )

        NavigationDrawerItem(
            onClick = {
                navController.navigate(Routes.TypeOfExpenseSettings.route)
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.drawer_type_of_expense_settings)) },
            selected = false,
        )

        NavigationDrawerItem(
            onClick = { exportToJsonLauncher.launch(exportJsonFileName) },
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.drawer_export_to_json)) },
            selected = false,
        )

        NavigationDrawerItem(
            onClick = { importFromJsonLauncher.launch(arrayOf("application/json")) },
            modifier = Modifier.padding(horizontal = 12.dp),
            label = { Text(text = stringResource(id = R.string.drawer_import_from_json)) },
            selected = false,
        )
    }
}
