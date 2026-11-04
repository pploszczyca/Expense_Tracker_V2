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
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpenseListViewModel
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpensesList
import com.github.pploszczyca.expensetrackerv2.features.main.features.list.ExpensesListEvent
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    mainViewStateFlow: StateFlow<MainViewModel.ViewState>,
) {
    val mainViewState by mainViewStateFlow.collectAsState()

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
                onEditExpenseButtonClicked = { expense ->
                    expense
                        .let(ExpensesListEvent::OnEditExpenseButtonClicked)
                        .let(expensesListViewModel::onEvent)
                }
            )
        }
    }
}

@Composable
private fun MainExpensesInformation(moneyInWalletAmount: Price) {
    androidx.compose.material3.ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.in_wallet),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Text(
                text = moneyInWalletAmount.toString(),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}
