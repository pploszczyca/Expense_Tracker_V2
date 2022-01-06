package com.example.expensetrackerv2

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.providers.SampleDataProvider
import com.example.expensetrackerv2.utilities.JSONUtils
import com.example.expensetrackerv2.utilities.MathUtils
import kotlinx.coroutines.launch

@Composable
fun MainExpensesInformation(expenseWithItsTypeList: List<ExpenseWithItsType> ) {
    val moneyInWalletAmount = MathUtils.roundToMoney(expenseWithItsTypeList.map { expense ->
        expense.price * expense.type.multiplier
    }.sum())

    Text(
        "In Wallet: $moneyInWalletAmount",
        Modifier.padding(5.dp),
        style = MaterialTheme.typography.h4
    )
}

@Composable
fun MainComposable(navController: NavController) {
    val currentContext = LocalContext.current
    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val expenses = expenseDao.getAllExpenses()
    val expenseWithItsTypeList = expenseDao.getAllExpenseWithItsType()

    if (expenseDao.getAllTypesOfExpense().isEmpty() && expenses.isEmpty()) {
        SampleDataProvider.sampleTypeOfExpense(currentContext)
        SampleDataProvider.sampleExpenses(currentContext)
    }

    val exportToJsonFileResult = remember { mutableStateOf<Uri?>(null) }
    val importFromJsonFileResult = remember { mutableStateOf<Uri?>(null) }

    val exportToJsonLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument()) {
        exportToJsonFileResult.value = it
    }

    val importFromJsonLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        importFromJsonFileResult.value = it
    }

    exportToJsonFileResult.value?.let { uri -> currentContext.contentResolver.openOutputStream(uri)?.write(JSONUtils.exportExpensesListToJson(expenses).toByteArray()) }
    importFromJsonFileResult.value?.let {uri ->
        coroutineScope.launch {
            expenseDao.deleteAllExpenses()
            expenseDao.insertAllExpenses(JSONUtils.importExpensesListFromJson(currentContext.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }!!))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                TextButton(onClick = { exportToJsonLauncher.launch("backup_expenses.json") }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Text(text = "Export to JSON")
                }

                TextButton(onClick = { importFromJsonLauncher.launch(arrayOf("application/json")) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Text(text = "Import from JSON")
                }
            }
        },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }

                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Localized description")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ExpenseForm.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainExpensesInformation(expenseWithItsTypeList)
                    ExpensesList(
                        expenseWithItsTypeList = expenseWithItsTypeList,
                        navController = navController
                    )
                }
            }
        }
    )
}