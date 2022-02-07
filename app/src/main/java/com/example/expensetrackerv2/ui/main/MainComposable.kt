package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.ui.bar.SearchTopAppBar
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
    actualExpenseMonthYearKey: ExpenseMonthYearKey? = null,
    titleToSearch: String = "",
    isMainExpenseInformationVisible: Boolean = true
) {
    Box(modifier = Modifier.padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isMainExpenseInformationVisible) {
                MainExpensesInformation(
                    expenseWithItsTypeRepository.getExpenses(actualExpenseMonthYearKey, titleToSearch)
                        .observeAsState(listOf()).value
                )
            }
            ExpensesList(
                expenseWithItsTypeRepository = expenseWithItsTypeRepository,
                navController = navController,
                expenseMonthYearKey = actualExpenseMonthYearKey,
                titleToSearch = titleToSearch
            )
        }
    }
}

@Composable
fun MainComposable(
    navController: NavController,
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository
) {
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

    var topBarVisibility by remember {
        mutableStateOf(false)
    }
    var titleToSearch by remember {
        mutableStateOf("")
    }

    val onSearchButtonClick = { topBarVisibility = true }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                onMonthButtonClick,
                closeDrawer,
                expenseWithItsTypeRepository,
                navController
            )
        },
        topBar = {
            if (topBarVisibility) {
                SearchTopAppBar(
                    onTrailingIconClick = {
                        topBarVisibility = false
                        titleToSearch = ""
                    },
                    onValueChange = { titleToSearch = it })
            }
        },
        bottomBar = {
            BottomBarContent(
                coroutineScope,
                scaffoldState,
                actualExpenseMonthYearKey,
                onSearchButtonClick
            )
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
                expenseWithItsTypeRepository,
                navController,
                actualExpenseMonthYearKey.value,
                titleToSearch,
                topBarVisibility.not()
            )
        }
    )
}