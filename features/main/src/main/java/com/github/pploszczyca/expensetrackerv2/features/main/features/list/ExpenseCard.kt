package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseTrackerV2Theme
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import java.time.LocalDate

@Composable
fun ExpenseCard(
    expense: Expense,
    modifier: Modifier = Modifier,
    onEditExpenseButtonClicked: (Expense) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .animateContentSize()
            .clickable { onEditExpenseButtonClicked(expense) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    text = expense.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                val formattedPrice = when (expense.type) {
                    Expense.Type.Income -> "+${expense.price}"
                    Expense.Type.Outgo -> "${expense.signedPrice}"
                }
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = formattedPrice,
                    color = if (expense.type == Expense.Type.Outgo) ExpenseColor else IncomeColor
                )
            }

            if (!expense.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expense.description.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            ExtraContentExpenseCard(
                expense = expense,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExtraContentExpenseCard(
    expense: Expense,
) {
    val hasCategory = expense.category != null
    val hasPlace = !expense.place.isNullOrBlank()
    if (!hasCategory && !hasPlace) return

    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        expense.category?.let { category ->
            ExtraContentRow(
                contentIcon = Icons.Filled.Category,
                contentString = category.name,
            )
        }
        expense.place?.takeIf { it.isNotBlank() }?.let { place ->
            ExtraContentRow(
                contentIcon = Icons.Default.Place,
                contentString = place,
            )
        }
    }
}

@Composable
private fun ExtraContentRow(
    contentIcon: ImageVector,
    contentString: String,
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = contentIcon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = contentString,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExpenseCardPreview() {
    val category = Category(
        id = Id.new(),
        name = "Category name",
    )
    val expense = Expense.new(
        title = "Expense title",
        price = Price.of(50.toBigDecimal()),
        date = ExpenseDate.of(LocalDate.now()),
        description = "Some description",
        place = "Place",
        type = Expense.Type.Outgo,
        category = category,
    )

    ExpenseTrackerV2Theme {
        ExpenseCard(expense = expense)
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExpenseCardWithLongTitlePreview() {
    val category = Category(
        id = Id.new(),
        name = "Category name",
    )
    val expense = Expense.new(
        title = "Long long long long long long long long title",
        price = Price.of(50.toBigDecimal()),
        date = ExpenseDate.of(LocalDate.now()),
        description = "Some description",
        place = "Place",
        type = Expense.Type.Outgo,
        category = category,
    )

    ExpenseTrackerV2Theme {
        ExpenseCard(expense = expense)
    }
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExtraContentRowPreview() {
    ExpenseTrackerV2Theme {
        Surface {
            ExtraContentRow(
                contentIcon = Icons.Default.Place,
                contentString = "Biedronka",
            )
        }
    }
}