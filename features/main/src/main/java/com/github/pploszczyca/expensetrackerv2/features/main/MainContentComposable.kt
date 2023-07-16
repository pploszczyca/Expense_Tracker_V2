package com.github.pploszczyca.expensetrackerv2.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            ExpensesList(
                viewModel = expensesListViewModel,
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
