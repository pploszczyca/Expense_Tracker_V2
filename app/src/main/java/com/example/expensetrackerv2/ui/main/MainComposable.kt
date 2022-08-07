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
import com.example.expensetrackerv2.ui.bar.SearchTopAppBar
import com.example.expensetrackerv2.ui.main.features.bottom_bar.BottomBarContent
import com.example.expensetrackerv2.ui.main.features.drawer.DrawerContent
import com.example.expensetrackerv2.ui.main.features.list.ExpensesList
import kotlinx.coroutines.launch

@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val mainViewState = viewModel.viewState

    val openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } }
    val closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val onMonthButtonClick = { key: ExpenseMonthYearKey ->
        closeDrawer()
        viewModel.onEvent(MainEvent.MonthYearKeyChange(key))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                onMonthButtonClick = onMonthButtonClick,
                onExportToJsonClick = { uri ->
                    viewModel.onEvent(
                        MainEvent.ExportToJsonButtonClick(uri)
                    )
                },
                onImportFromJsonClick = { uri ->
                    viewModel.onEvent(
                        MainEvent.ImportFromJsonButtonClick(uri)
                    )
                },
                closeDrawer = closeDrawer,
                monthYearKeyList = mainViewState.monthYearKeys,
                navController = navController
            )
        },
        topBar = {
            if (mainViewState.isTopBarVisible) {
                SearchTopAppBar(
                    searchedValue = mainViewState.searchedTitle,
                    onTrailingIconClick = { viewModel.onEvent(MainEvent.OnTopBarTrailingIconClick) },
                    onValueChange = { viewModel.onEvent(MainEvent.SearchedTitleChange(it)) })
            }
        },
        bottomBar = {
            BottomBarContent(
                onMenuButtonClick = { openDrawer() },
                isClearButtonVisible = mainViewState.clearButtonVisible,
                onClearButtonClick = { viewModel.onEvent(MainEvent.BottomBar.ClearButtonClick) },
                onSearchButtonClick = { viewModel.onEvent(MainEvent.BottomBar.SearchButtonClick) })
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
                mainViewState = mainViewState,
                navController = navController,
                onDeleteButtonClick = { viewModel.onEvent(MainEvent.DeleteButtonClick(it)) },
                onDismissDeleteButtonClick = { viewModel.onEvent(MainEvent.DismissDeleteButtonClick) },
                onConfirmDeleteButtonClick = { viewModel.onEvent(MainEvent.ConfirmDeleteButtonClick) })
        }
    )
}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    mainViewState: MainViewModel.ViewState,
    navController: NavController,
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
            if (mainViewState.mainExpenseInformationVisible) {
                MainExpensesInformation(
                    moneyInWalletAmount = mainViewState.moneyInWalletAmount
                )
            }
            ExpensesList(
                mainViewState = mainViewState,
                navController = navController,
                onDeleteButtonClick = onDeleteButtonClick,
                onDismissDeleteButtonClick = onDismissDeleteButtonClick,
                onConfirmDeleteButtonClick = onConfirmDeleteButtonClick,
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
