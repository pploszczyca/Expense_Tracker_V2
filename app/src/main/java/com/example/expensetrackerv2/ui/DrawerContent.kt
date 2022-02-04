package com.example.expensetrackerv2.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.utilities.DateUtils
import com.example.expensetrackerv2.utilities.JSONUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

@Composable
private fun ShowMonthButtons(
    expenseWithItsTypeList: List<ExpenseWithItsType>,
    onClick: (expenseMonthYearKey: ExpenseMonthYearKey) -> Unit
) {
    LazyColumn {
        items(expenseWithItsTypeList
            .groupBy { it.getKey() }
            .map { it.key }
            .distinct())
        { key ->
            TextButton(
                onClick = { onClick(key) }, modifier = Modifier
                    .padding(8.dp)
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
    closeDrawer: () -> Job
) {
    val currentContext = LocalContext.current
    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()
    val expenseWithItsTypeList = expenseDao.getAllExpenseWithItsType()
    val exportJsonFileName = stringResource(id = R.string.drawer_months_title)

    val coroutineScope = rememberCoroutineScope()

    val exportToJsonFileResult = remember { mutableStateOf<Uri?>(null) }
    val importFromJsonFileResult = remember { mutableStateOf<Uri?>(null) }

    val exportToJsonLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument()) {
            exportToJsonFileResult.value = it
        }

    val importFromJsonLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            importFromJsonFileResult.value = it
        }

    exportToJsonFileResult.value?.let { uri ->
        closeDrawer()
        currentContext.contentResolver.openOutputStream(uri)
            ?.write(JSONUtils.exportExpensesListToJson(expenseDao.getAllExpenses()).toByteArray())
    }
    importFromJsonFileResult.value?.let { uri ->
        closeDrawer()
        coroutineScope.launch {
            expenseDao.deleteAllExpenses()
            expenseDao.insertAllExpenses(
                JSONUtils.importExpensesListFromJson(
                    currentContext.contentResolver.openInputStream(
                        uri
                    )?.bufferedReader()?.use { it.readText() }!!
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Text(stringResource(id = R.string.drawer_months_title), style = MaterialTheme.typography.h5, modifier = Modifier.padding(16.dp))

        ShowMonthButtons(expenseWithItsTypeList, onMonthButtonClick)

        Text(stringResource(id = R.string.drawer_options_title), style = MaterialTheme.typography.h5, modifier = Modifier.padding(16.dp))

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