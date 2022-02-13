package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.ui.bar.SearchTopAppBar
import com.example.expensetrackerv2.ui.main.list.ExpensesList
import com.example.expensetrackerv2.utilities.MathUtils
import kotlinx.coroutines.launch

@Composable
private fun MainExpensesInformation(moneyInWalletAmount: Double) {
    Text(
        "${stringResource(id = R.string.in_wallet)} $moneyInWalletAmount",
        Modifier.padding(5.dp),
        style = MaterialTheme.typography.h4
    )
}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    expenseWithItsTypeList: List<ExpenseWithItsType>,
    navController: NavController,
    isMainExpenseInformationVisible: Boolean,
    isDeleteDialogVisible: Boolean,
    onDeleteButtonClick: (ExpenseWithItsType) -> Unit,
    onDismissDeleteButtonClick: () -> Unit,
    onConfirmDeleteButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isMainExpenseInformationVisible) {
                MainExpensesInformation(
                    moneyInWalletAmount = MathUtils.sumMoneyInList(
                        expenseWithItsTypeList
                    )
                )
            }
            ExpensesList(
                expenseWithItsTypeList = expenseWithItsTypeList,
                navController = navController,
                onDeleteButtonClick = onDeleteButtonClick,
                onDismissDeleteButtonClick = onDismissDeleteButtonClick,
                onConfirmDeleteButtonClick = onConfirmDeleteButtonClick,
                isDeleteDialogVisible = isDeleteDialogVisible
            )
        }
    }
}

@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val searchedTitle by viewModel.searchedTitle
    val currentMonthYearKey by viewModel.currentMonthYearKey

    fun checkIfHasKeyAndContainsSearchedTitle(expenseWithItsType: ExpenseWithItsType): Boolean =
        (currentMonthYearKey == null || expenseWithItsType.getKey() == currentMonthYearKey) && expenseWithItsType.title.contains(
            searchedTitle
        )

    val expensesWithItsTypeList =
        viewModel.expensesWithItsType.collectAsState(initial = emptyList()).value.filter {
            checkIfHasKeyAndContainsSearchedTitle(
                it
            )
        }

    val isTopBarVisible by viewModel.isTopBarVisible
    val isDeleteDialogVisible by viewModel.isDeleteDialogVisible

    val openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } }
    val closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val onMonthButtonClick = { key: ExpenseMonthYearKey ->
        closeDrawer()
        viewModel.onEvent(MainEvent.MonthYearKeyChange(key))
    }

    val closeSearchTopAppBar = {
        viewModel.onEvent(MainEvent.TopBarVisibilityChange(false))
        viewModel.onEvent(MainEvent.SearchedTitleChange(""))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                onMonthButtonClick = onMonthButtonClick,
                onExportToJsonClick = { uri ->
                    viewModel.onEvent(
                        MainEvent.ExportToJsonButtonClick(
                            uri
                        )
                    )
                },
                onImportFromJsonClick = { uri ->
                    viewModel.onEvent(
                        MainEvent.ImportFromJsonButtonClick(
                            uri
                        )
                    )
                },
                closeDrawer = closeDrawer,
                monthYearKeyList = expensesWithItsTypeList.map { it.getKey() }.distinct(),
                navController = navController
            )
        },
        topBar = {
            if (isTopBarVisible) {
                SearchTopAppBar(
                    searchedValue = searchedTitle,
                    onTrailingIconClick = closeSearchTopAppBar,
                    onValueChange = { viewModel.onEvent(MainEvent.SearchedTitleChange(it)) })
            }
        },
        bottomBar = {
            BottomBarContent(
                onMenuButtonClick = { openDrawer() },
                isClearButtonVisible = viewModel.isClearButtonVisible(),
                onClearButtonClick = { viewModel.onEvent(MainEvent.MonthYearKeyChange(null)) },
                onSearchButtonClick = { viewModel.onEvent(MainEvent.TopBarVisibilityChange(true)) })
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
                innerPadding = innerPadding,
                expenseWithItsTypeList = expensesWithItsTypeList,
                navController = navController,
                isMainExpenseInformationVisible = viewModel.isMainExpenseInformationVisible(),
                isDeleteDialogVisible = isDeleteDialogVisible,
                onDeleteButtonClick = { viewModel.onEvent(MainEvent.DeleteButtonClick(it)) },
                onDismissDeleteButtonClick = { viewModel.onEvent(MainEvent.DismissDeleteButtonClick()) },
                onConfirmDeleteButtonClick = { viewModel.onEvent(MainEvent.ConfirmDeleteButtonClick()) })
        }
    )
}