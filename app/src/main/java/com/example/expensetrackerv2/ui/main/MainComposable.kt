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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.bar.SearchTopAppBar
import com.example.expensetrackerv2.ui.main.features.bottom_bar.BottomBarContent
import com.example.expensetrackerv2.ui.main.features.delete_dialog.DeleteExpenseAlertDialog
import com.example.expensetrackerv2.ui.main.features.delete_dialog.DeleteExpenseDialogViewModel
import com.example.expensetrackerv2.ui.main.features.drawer.DrawerContent
import com.example.expensetrackerv2.ui.main.features.list.ExpenseListViewModel
import com.example.expensetrackerv2.ui.main.features.list.ExpensesList
import com.example.expensetrackerv2.ui.main.features.list.ExpensesListEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val mainViewState = viewModel.viewState

    viewModel.openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } }
    val closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                onMonthButtonClick = { key: ExpenseMonthYearKey ->
                    closeDrawer()
                    viewModel.onEvent(MainEvent.MonthYearKeyChange(key))
                },
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
                viewModel = hiltViewModel(),
                isClearButtonVisible = mainViewState.clearButtonVisible,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ExpenseForm.route)
            }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_icon))
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
    val deleteExpenseDialogViewModel: DeleteExpenseDialogViewModel = hiltViewModel()
    mainViewState.expenseToDelete?.let {
        deleteExpenseDialogViewModel.init(
            expenseWithItsType = it
        )
    }

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
            val expensesListViewModel: ExpenseListViewModel = hiltViewModel()
            expensesListViewModel.onEvent(ExpensesListEvent.ExpensesChanged(mainViewState.filteredExpenses))
            ExpensesList(
                viewModel = expensesListViewModel,
                navController = navController,
                onDeleteButtonClick = onDeleteButtonClick,
            )
        }
    }

    if (mainViewState.isDeleteDialogVisible) {
        DeleteExpenseAlertDialog(
            viewModel = deleteExpenseDialogViewModel,
            onDismissClick = onDismissDeleteButtonClick,
            onConfirmButtonClick = onConfirmDeleteButtonClick
        )
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
