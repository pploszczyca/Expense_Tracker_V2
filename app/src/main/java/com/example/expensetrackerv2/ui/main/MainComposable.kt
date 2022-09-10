package com.example.expensetrackerv2.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.bar.SearchTopAppBar
import com.example.expensetrackerv2.ui.main.features.bottom_bar.BottomBarContent
import com.example.expensetrackerv2.ui.main.features.delete_dialog.DeleteExpenseAlertDialog
import com.example.expensetrackerv2.ui.main.features.delete_dialog.DeleteExpenseDialogViewModel
import com.example.expensetrackerv2.ui.main.features.drawer.DrawerContent
import com.example.expensetrackerv2.ui.main.features.filter_dialog.MainFilterDialog
import com.example.expensetrackerv2.ui.main.features.filter_dialog.MainFilterDialogViewModel
import com.example.expensetrackerv2.ui.main.features.list.ExpenseListViewModel
import com.example.expensetrackerv2.ui.main.features.list.ExpensesList
import com.example.expensetrackerv2.ui.main.features.list.ExpensesListEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val mainViewState = viewModel.viewState

    viewModel.openDrawer = {
        coroutineScope.launch { drawerState.open() }
    }
    val closeDrawer = {
        coroutineScope.launch { drawerState.close() }
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
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
                navController = navController
            )
        },
    ) {
        Scaffold(
            topBar = {
                if (mainViewState.topBarVisible) {
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
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_icon)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                MainContent(
                    innerPadding = innerPadding,
                    mainViewState = mainViewState,
                    navController = navController,
                    onDeleteButtonClick = { viewModel.onEvent(MainEvent.DeleteButtonClick(it)) },
                    onDismissDeleteButtonClick = { viewModel.onEvent(MainEvent.DismissDeleteButtonClick) },
                    onConfirmDeleteButtonClick = { viewModel.onEvent(MainEvent.ConfirmDeleteButtonClick) }
                )
            }
        )
    }
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
    val filterDialogViewModel: MainFilterDialogViewModel = hiltViewModel()
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

    if (mainViewState.deleteDialogVisible) {
        DeleteExpenseAlertDialog(
            viewModel = deleteExpenseDialogViewModel,
            onDismissClick = onDismissDeleteButtonClick,
            onConfirmButtonClick = onConfirmDeleteButtonClick
        )
    }

    if (mainViewState.filterDialogVisible) {
        MainFilterDialog(viewModel = filterDialogViewModel)
    }
}

@Composable
private fun MainExpensesInformation(moneyInWalletAmount: Double) {
    Text(
        "${stringResource(id = R.string.in_wallet)} $moneyInWalletAmount",
        Modifier.absolutePadding(left = 12.dp, bottom = 16.dp, top = 16.dp),
        style = MaterialTheme.typography.displayMedium
    )
}
