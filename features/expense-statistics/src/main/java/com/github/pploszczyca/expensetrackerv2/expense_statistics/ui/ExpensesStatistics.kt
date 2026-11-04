package com.github.pploszczyca.expensetrackerv2.expense_statistics.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.bar.TopAppBarWithBack
import com.github.pploszczyca.expensetrackerv2.common_ui.calendar_field.CalendarDialogField
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.domain.sumOf
import com.github.pploszczyca.expensetrackerv2.domain.total
import com.github.pploszczyca.expensetrackerv2.domain.totalIncome
import com.github.pploszczyca.expensetrackerv2.domain.totalOutgo
import com.github.pploszczyca.expensetrackerv2.expense_statistics.view_model.DateRangePreset
import com.github.pploszczyca.expensetrackerv2.expense_statistics.view_model.ExpensesStatisticsViewModel
import com.github.pploszczyca.expensetrackerv2.features.expense_statistics.R
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ExpensesStatistics(
    viewModel: ExpensesStatisticsViewModel,
) {
    val fromDate by viewModel.fromDate
    val toDate by viewModel.toDate
    val fromLocalDate by viewModel.fromLocalDate
    val toLocalDate by viewModel.toLocalDate
    val selectedPreset by viewModel.selectedPreset
    val allExpenses by viewModel.expensesWithItsType.collectAsState(emptyList())

    val filteredExpenses = when (selectedPreset) {
        DateRangePreset.ALL_TIME -> allExpenses
        else -> allExpenses.filter { expense ->
            !expense.date.date.isBefore(fromLocalDate) && !expense.date.date.isAfter(toLocalDate)
        }
    }

    val uncategorizedLabel = stringResource(id = R.string.uncategorized)
    val totalOutgoPrice = filteredExpenses.totalOutgo
    val categoryBreakdown = filteredExpenses
        .filter { it.type == Expense.Type.Outgo }
        .groupBy { it.category?.name ?: uncategorizedLabel }
        .map { (categoryName, expenses) ->
            val categorySum = expenses.sumOf { it.price }
            val fraction = if (totalOutgoPrice.amount > BigDecimal.ZERO) {
                categorySum.amount.divide(totalOutgoPrice.amount, 4, RoundingMode.HALF_UP).toFloat()
            } else {
                0f
            }
            CategoryStatItem(
                name = categoryName,
                amount = categorySum,
                fraction = fraction.coerceIn(0f, 1f),
            )
        }
        .sortedByDescending { it.amount.amount }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.expense_statistics_app_bar_title),
                onBackClicked = viewModel::onBackButtonClicked,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedPreset == DateRangePreset.THIS_MONTH,
                        onClick = { viewModel.onPresetSelected(DateRangePreset.THIS_MONTH) },
                        label = { Text(stringResource(id = R.string.preset_this_month)) }
                    )
                    FilterChip(
                        selected = selectedPreset == DateRangePreset.LAST_7_DAYS,
                        onClick = { viewModel.onPresetSelected(DateRangePreset.LAST_7_DAYS) },
                        label = { Text(stringResource(id = R.string.preset_last_7_days)) }
                    )
                    FilterChip(
                        selected = selectedPreset == DateRangePreset.THIS_YEAR,
                        onClick = { viewModel.onPresetSelected(DateRangePreset.THIS_YEAR) },
                        label = { Text(stringResource(id = R.string.preset_this_year)) }
                    )
                    FilterChip(
                        selected = selectedPreset == DateRangePreset.ALL_TIME,
                        onClick = { viewModel.onPresetSelected(DateRangePreset.ALL_TIME) },
                        label = { Text(stringResource(id = R.string.preset_all_time)) }
                    )
                }

                if (selectedPreset != DateRangePreset.ALL_TIME) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CalendarDialogField(
                            date = fromDate,
                            label = stringResource(id = R.string.from_date),
                            modifier = Modifier.weight(1f),
                            onDatePickerPick = viewModel::onFromDateChange,
                        )

                        CalendarDialogField(
                            date = toDate,
                            label = stringResource(id = R.string.to_date),
                            modifier = Modifier.weight(1f),
                            onDatePickerPick = viewModel::onToDateChange,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                StatisticsCard(
                    title = stringResource(id = R.string.total),
                    number = filteredExpenses.total.toString(),
                    color = Color.Unspecified
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    StatisticsCard(
                        title = stringResource(id = R.string.total_expenses),
                        number = totalOutgoPrice.toString(),
                        color = ExpenseColor,
                        modifier = Modifier.weight(1f),
                    )

                    StatisticsCard(
                        title = stringResource(id = R.string.total_incomes),
                        number = filteredExpenses.totalIncome.toString(),
                        color = IncomeColor,
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.spending_by_category),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )

                if (categoryBreakdown.isEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_expenses_in_period),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                } else {
                    categoryBreakdown.forEach { item ->
                        CategoryBreakdownCard(item = item)
                    }
                }
            }
        },
    )
}

private data class CategoryStatItem(
    val name: String,
    val amount: Price,
    val fraction: Float,
)

@Composable
private fun CategoryBreakdownCard(item: CategoryStatItem) {
    val percentage = (item.fraction * 100).toInt()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "${item.amount} ($percentage%)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = ExpenseColor,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { item.fraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = ExpenseColor,
            )
        }
    }
}

