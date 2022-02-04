package com.example.expensetrackerv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.providers.SampleDataProvider
import com.example.expensetrackerv2.utilities.MathUtils
import kotlinx.coroutines.launch

@Composable
private fun MainExpensesInformation(expenseWithItsTypeList: MutableState<List<ExpenseWithItsType>>) {
    val moneyInWalletAmount = MathUtils.sumMoneyInList(expenseWithItsTypeList.value)

    Text(
        "${stringResource(id = R.string.in_wallet)} $moneyInWalletAmount",
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
    val expenseWithItsTypeList = remember {
        mutableStateOf(expenseDao.getAllExpenseWithItsType())
    }
    val isFiltered = remember {
        mutableStateOf(false)
    }

    if (expenseDao.getAllTypesOfExpense().isEmpty() && expenses.isEmpty()) {
        SampleDataProvider.sampleTypeOfExpense(currentContext)
        SampleDataProvider.sampleExpenses(currentContext)
    }

    val closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val onMonthButtonClick = { expenseMonthYearKey: ExpenseMonthYearKey ->
        closeDrawer()
        isFiltered.value = true
        expenseWithItsTypeList.value =
            expenseDao.getAllExpenseWithItsType().filter { it.getKey() == expenseMonthYearKey }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { DrawerContent(onMonthButtonClick, closeDrawer) },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon))
                }

                Spacer(Modifier.weight(1f, true))

                if (isFiltered.value) {
                    IconButton(onClick = {
                        expenseWithItsTypeList.value = expenseDao.getAllExpenseWithItsType()
                        isFiltered.value = false
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = stringResource(id = R.string.reset_icon))
                    }
                }

                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.search_icon))
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ExpenseForm.route)
            }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_icon))
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