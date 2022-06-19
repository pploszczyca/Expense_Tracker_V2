package com.example.expensetrackerv2.ui.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.Job
import java.util.*

@Composable
private fun ShowMonthButtons(
    monthYearKeyList: List<ExpenseMonthYearKey>,
    onClick: (expenseMonthYearKey: ExpenseMonthYearKey) -> Unit
) {
    Column {
        monthYearKeyList.map { key ->
            TextButton(
                onClick = { onClick(key) }, modifier = Modifier
                    .absolutePadding(left = 8.dp, bottom = 3.dp, top = 3.dp)
            ) {
                val date = Date()
                date.month = key.month
                date.year = key.year
                Text(text = DateUtils.dateToStringWithMonthAndYear(date))
            }
        }
    }
}

@Composable
fun DrawerContent(
    onMonthButtonClick: (expenseMonthYearKey: ExpenseMonthYearKey) -> Unit,
    onExportToJsonClick: (Uri?) -> Unit,
    onImportFromJsonClick: (Uri?) -> Unit,
    closeDrawer: () -> Job,
    monthYearKeyList: List<ExpenseMonthYearKey>,
    navController: NavController,
) {
    val exportJsonFileName = stringResource(id = R.string.drawer_months_title)

    val exportToJsonLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument()) {
            onExportToJsonClick(it)
            closeDrawer()
        }

    val importFromJsonLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            onImportFromJsonClick(it)
            closeDrawer()
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            stringResource(id = R.string.drawer_months_title),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )

        ShowMonthButtons(monthYearKeyList, onMonthButtonClick)

        Text(
            stringResource(id = R.string.drawer_options_title),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )

        TextButton(onClick = {
            navController.navigate(Routes.ExpenseStatistics.route)
            closeDrawer()
        }, modifier = Modifier.padding(8.dp)) {
            Text(text = stringResource(id = R.string.drawer_statistics))
        }

        TextButton(onClick = {
            navController.navigate(Routes.TypeOfExpenseSettings.route)
            closeDrawer()
        }, modifier = Modifier.padding(8.dp)) {
            Text(text = stringResource(id = R.string.drawer_type_of_expense_settings))
        }

        TextButton(
            onClick = { exportToJsonLauncher.launch(exportJsonFileName) }, modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.drawer_export_to_json))
        }

        TextButton(
            onClick = { importFromJsonLauncher.launch(arrayOf("application/json")) },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.drawer_import_from_json))
        }
    }
}