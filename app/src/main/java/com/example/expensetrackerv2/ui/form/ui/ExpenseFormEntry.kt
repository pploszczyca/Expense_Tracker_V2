package com.example.expensetrackerv2.ui.form.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.ui.common_components.bar.TopAppBarWithBack
import com.example.expensetrackerv2.ui.form.view_model.ExpenseFormViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExpenseFormEntry(
    viewModel: ExpenseFormViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    val snackBarHostState = remember(::SnackbarHostState)

    HandleRouteActions(
        routeAction = viewModel.routeActions,
        snackBarHostState = snackBarHostState,
    )

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.expense_form),
                onBackClicked = viewModel::onBackClicked,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            ExpenseForm(
                viewState = viewState,
                onTitleChanged = viewModel::onTitleChanged,
                onPriceChanged = viewModel::onPriceChanged,
                onCategoryChanged = viewModel::onCategoryChanged,
                onDateChanged = viewModel::onDateChanged,
                onPlaceNameChanged = viewModel::onPlaceNameChanged,
                onDescriptionChanged = viewModel::onDescriptionChanged,
                onSubmitButtonClicked = viewModel::onSubmitButtonClicked,
            )
        }
    }
}

@Composable
fun HandleRouteActions(
    routeAction: SharedFlow<ExpenseFormViewModel.RouteAction>,
    snackBarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        routeAction
            .collectLatest {
                when (it) {
                    ExpenseFormViewModel.RouteAction.ShowSnackBar ->
                        snackBarHostState.showSnackbar("Something is wrong :(((")
                }
            }
    }
}
