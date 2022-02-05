package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeDatabaseRepository
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.ui.main.list.ExpensesList
import com.example.expensetrackerv2.utilities.MathUtils
import kotlinx.coroutines.launch

@Composable
private fun MainExpensesInformation(expenseWithItsTypeList: List<ExpenseWithItsType>) {
    val moneyInWalletAmount = MathUtils.sumMoneyInList(expenseWithItsTypeList)

    Text(
        "${stringResource(id = R.string.in_wallet)} $moneyInWalletAmount",
        Modifier.padding(5.dp),
        style = MaterialTheme.typography.h4
    )
}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    navController: NavController,
    actualExpenseMonthYearKey: ExpenseMonthYearKey?
) {
    Box(modifier = Modifier.padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainExpensesInformation(
                expenseWithItsTypeRepository.getExpenses(actualExpenseMonthYearKey)
                    .observeAsState(listOf()).value
            )
            ExpensesList(
                expenseWithItsTypeRepository = expenseWithItsTypeRepository,
                navController = navController,
                expenseMonthYearKey = actualExpenseMonthYearKey
            )
        }
    }
}

@Composable
fun MainComposable(navController: NavController) {
    val currentContext = LocalContext.current
    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()
    val expenseWithItsTypeDatabaseRepository = ExpenseWithItsTypeDatabaseRepository(expenseDao)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val actualExpenseMonthYearKey = remember {
        mutableStateOf<ExpenseMonthYearKey?>(null)
    }

    val closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val onMonthButtonClick = { expenseMonthYearKey: ExpenseMonthYearKey ->
        closeDrawer()
        actualExpenseMonthYearKey.value = expenseMonthYearKey
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                onMonthButtonClick,
                closeDrawer,
                expenseWithItsTypeDatabaseRepository
            )
        },
        bottomBar = {
            BottomBarContent(coroutineScope, scaffoldState, actualExpenseMonthYearKey)
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
            MainContent(
                innerPadding,
                expenseWithItsTypeDatabaseRepository,
                navController,
                actualExpenseMonthYearKey.value
            )
        }
    )
}