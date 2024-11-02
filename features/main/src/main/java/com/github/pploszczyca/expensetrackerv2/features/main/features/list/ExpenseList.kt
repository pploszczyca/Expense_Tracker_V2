package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseTrackerV2Theme
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Price
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(
    viewState: ExpenseListViewModel.ViewState,
    onDeleteButtonClick: (Expense) -> Unit,
    onEditExpenseButtonClicked: (Expense) -> Unit,
) {
    LazyColumn(Modifier.padding(3.dp)) {
        viewState.filteredDailyExpenses.forEach { dailyExpense ->
            stickyHeader {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        dailyExpense.date,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row {
                        Text(
                            dailyExpense.totalOutgo,
                            style = MaterialTheme.typography.titleMedium,
                            color = ExpenseColor
                        )
                        Text("/", style = MaterialTheme.typography.titleMedium)
                        Text(
                            dailyExpense.totalIncome,
                            style = MaterialTheme.typography.titleMedium,
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
                    onDeleteButtonClick = onDeleteButtonClick,
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
            onDeleteButtonClick = {},
            onEditExpenseButtonClicked = {}
        )
    }
}