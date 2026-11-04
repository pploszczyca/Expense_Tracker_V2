package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseTrackerV2Theme
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.features.main.R
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewState: ExpenseListViewModel.ViewState,
    onEditExpenseButtonClicked: (Expense) -> Unit,
) {
    if (viewState.filteredDailyExpenses.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.empty_expenses_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.empty_expenses_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        }
        return
    }

    LazyColumn(Modifier.padding(horizontal = 4.dp)) {
        viewState.filteredDailyExpenses.forEach { dailyExpense ->
            stickyHeader {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = dailyExpense.date,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "-${dailyExpense.totalOutgo}",
                            style = MaterialTheme.typography.labelLarge,
                            color = ExpenseColor
                        )
                        Text(
                            text = "+${dailyExpense.totalIncome}",
                            style = MaterialTheme.typography.labelLarge,
                            color = IncomeColor,
                        )
                    }
                }
            }

            items(
                items = dailyExpense.expenses,
                key = { it.id.toString() },
            ) { expense ->
                ExpenseCard(
                    modifier = Modifier.animateContentSize(),
                    expense = expense,
                    onEditExpenseButtonClicked = onEditExpenseButtonClicked,
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExpensesListPreview() {
    ExpenseTrackerV2Theme {
        ExpensesList(
            viewState = ExpenseListViewModel.ViewState(
                dailyExpenses = listOf(
                    ExpenseListViewModel.ViewState.DailyExpense(
                        date = "2021-01-01",
                        expenses = listOf(
                            Expense.new(
                                title = "Expense title",
                                price = Price.of(50.toBigDecimal()),
                                date = ExpenseDate.of(LocalDate.now()),
                                description = "Some description",
                                place = "Place",
                                type = Expense.Type.Outgo,
                                category = Category.new(
                                    name = "Category name",
                                ),
                            ),
                            Expense.new(
                                title = "Expense title",
                                price = Price.of(50.toBigDecimal()),
                                date = ExpenseDate.of(LocalDate.now()),
                                description = "Some description",
                                place = "Place",
                                type = Expense.Type.Income,
                                category = Category.new(
                                    name = "Category name",
                                ),
                            )
                        ),
                        totalIncome = "100",
                        totalOutgo = "100"
                    )
                )
            ),
            onEditExpenseButtonClicked = {}
        )
    }
}