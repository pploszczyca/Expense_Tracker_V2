package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.pploszczyca.expensetrackerv2.common_ui.bar.SearchTopAppBar
import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.BottomBarContent
import com.github.pploszczyca.expensetrackerv2.features.main.features.drawer.DrawerContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainComposable(
    viewModel: MainViewModel,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val mainViewState by viewModel.viewState.collectAsState()

    HandleRouteActions(
        routeAction = viewModel.routeAction,
        drawerState = drawerState,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onStatisticsItemClicked = {
                    viewModel.onEvent(MainEvent.OnStatisticsItemClicked)
                },
                onCategorySettingsItemClicked = {
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
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { viewModel.onEvent(MainEvent.OnAddNewExpenseButtonClicked) },
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.add_icon)
                            )
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                MainContent(
                    innerPadding = innerPadding,
                    mainViewStateFlow = viewModel.viewState,
                )
            }
        )
    }
}

@Composable
private fun HandleRouteActions(
    routeAction: Flow<MainViewModel.RouteAction>,
    drawerState: DrawerState,
) {
    LaunchedEffect(Unit) {
        routeAction
            .collectLatest {
                when (it) {
                    is MainViewModel.RouteAction.OpenDrawer -> drawerState.open()
                    is MainViewModel.RouteAction.CloseDrawer -> drawerState.close()
                }
            }
    }
}