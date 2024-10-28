package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.features.main.features.delete_dialog.DeleteExpenseAlertDialog
import com.github.pploszczyca.expensetrackerv2.features.main.features.delete_dialog.DeleteExpenseDialogViewModel
import com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog.MainFilterDialog
import com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog.MainFilterDialogViewModel
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpenseListViewModel
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpensesList
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpensesListEvent
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    mainViewStateFlow: StateFlow<MainViewModel.ViewState>,
    onDeleteButtonClick: (Expense) -> Unit,
    onDismissDeleteButtonClick: () -> Unit,
    onConfirmDeleteButtonClick: () -> Unit,
) {
    val deleteExpenseDialogViewModel: DeleteExpenseDialogViewModel = hiltViewModel()
    val filterDialogViewModel: MainFilterDialogViewModel = hiltViewModel()
    val mainViewState by mainViewStateFlow.collectAsState()

    mainViewState.expenseToDelete?.let {
        deleteExpenseDialogViewModel.init(
            expense = it
        )
    }

    Box(modifier = Modifier.padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
        ) {
            AnimatedVisibility(visible = mainViewState.mainExpenseInformationVisible) {
                MainExpensesInformation(
                    moneyInWalletAmount = mainViewState.moneyInWalletAmount
                )
            }
            val expensesListViewModel: ExpenseListViewModel = hiltViewModel()
            expensesListViewModel.onEvent(ExpensesListEvent.OnInit(mainViewStateFlow))

            val expensesListViewState by expensesListViewModel.viewState.collectAsState()

            ExpensesList(
                viewState = expensesListViewState,
                onDeleteButtonClick = onDeleteButtonClick,
                onEditExpenseButtonClicked = { expense ->
                    expense
                        .let(ExpensesListEvent::OnEditExpenseButtonClicked)
                        .let(expensesListViewModel::onEvent)
                }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(id = R.string.in_wallet),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
            )
            Text(
                moneyInWalletAmount.toString(),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}
