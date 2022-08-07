package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.expensetrackerv2.ui.main.features.drawer.DrawerContent
import com.example.expensetrackerv2.ui.main.features.bottom_bar.BottomBarContent
import com.example.expensetrackerv2.ui.main.features.list.ExpensesList
import com.example.expensetrackerv2.utilities.MathUtils
import kotlinx.coroutines.launch

@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val viewState = viewModel.viewState

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
                monthYearKeyList = viewState.monthYearKeys,
                navController = navController
            )
        },
        topBar = {
            if (viewState.isTopBarVisible) {
                SearchTopAppBar(
                    searchedValue = viewState.searchedTitle,
                    onTrailingIconClick = closeSearchTopAppBar,
                    onValueChange = { viewModel.onEvent(MainEvent.SearchedTitleChange(it)) })
            }
        },
        bottomBar = {
            BottomBarContent(
                onMenuButtonClick = { openDrawer() },
                isClearButtonVisible = viewState.clearButtonVisible,
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
                expenseWithItsTypeList = viewState.filteredExpenses,
                navController = navController,
                isMainExpenseInformationVisible = viewState.mainExpenseInformationVisible,
                isDeleteDialogVisible = viewState.isDeleteDialogVisible,
                onDeleteButtonClick = { viewModel.onEvent(MainEvent.DeleteButtonClick(it)) },
                onDismissDeleteButtonClick = { viewModel.onEvent(MainEvent.DismissDeleteButtonClick()) },
                onConfirmDeleteButtonClick = { viewModel.onEvent(MainEvent.ConfirmDeleteButtonClick()) })
        }
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
private fun MainExpensesInformation(moneyInWalletAmount: Double) {
    Text(
        "${stringResource(id = R.string.in_wallet)} $moneyInWalletAmount",
        Modifier.absolutePadding(left = 12.dp, bottom = 16.dp, top = 16.dp),
        style = MaterialTheme.typography.h4
    )
}
