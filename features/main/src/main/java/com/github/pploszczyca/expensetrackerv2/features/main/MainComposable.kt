package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.pploszczyca.expensetrackerv2.common_ui.bar.SearchTopAppBar
import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.BottomBarContent
import com.github.pploszczyca.expensetrackerv2.features.main.features.drawer.DrawerContent
import kotlinx.coroutines.launch

@Composable
fun MainComposable(
    viewModel: MainViewModel,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val mainViewState by viewModel.viewState.collectAsState()

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
                onStatisticsItemClicked = {
                    closeDrawer()
                    viewModel.onEvent(MainEvent.OnStatisticsItemClicked)
                },
                onCategorySettingsItemClicked = {
                    closeDrawer()
                    viewModel.onEvent(MainEvent.OnCategorySettingsItemClicked)
                },
            )
        },
    ) {
        Scaffold(
            topBar = {
                AnimatedVisibility(visible = mainViewState.topBarVisible) {
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
                    viewModel.onEvent(MainEvent.OnAddNewExpenseButtonClicked)
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
                    mainViewStateFlow = viewModel.viewState,
                    onDeleteButtonClick = { viewModel.onEvent(MainEvent.DeleteButtonClick(it)) },
                    onDismissDeleteButtonClick = { viewModel.onEvent(MainEvent.DismissDeleteButtonClick) },
                    onConfirmDeleteButtonClick = { viewModel.onEvent(MainEvent.ConfirmDeleteButtonClick) }
                )
            }
        )
    }
}
