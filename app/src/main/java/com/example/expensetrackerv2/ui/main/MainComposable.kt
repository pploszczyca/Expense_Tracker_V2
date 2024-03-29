package com.example.expensetrackerv2.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.navigation.Routes
import com.example.expensetrackerv2.ui.common_components.bar.SearchTopAppBar
import com.example.expensetrackerv2.ui.main.features.bottom_bar.BottomBarContent
import com.example.expensetrackerv2.ui.main.features.drawer.DrawerContent
import kotlinx.coroutines.launch

@Composable
fun MainComposable(
    navController: NavController,
    viewModel: MainViewModel,
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
