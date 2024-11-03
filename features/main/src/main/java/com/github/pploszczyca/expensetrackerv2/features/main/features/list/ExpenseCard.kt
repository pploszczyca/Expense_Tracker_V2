package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
            .padding(4.dp)
            .animateContentSize()
            .clickable { onEditExpenseButtonClicked(expense) }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = expense.title,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = expense.signedPrice.toString(),
                        color = if (expense.type == Expense.Type.Outgo) ExpenseColor else IncomeColor
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }

            ExtraContentExpenseCard(
                expense = expense,
            )
        }
    }
}

@Composable
private fun ExtraContentExpenseCard(
    expense: Expense,
) {
    expense.category?.let { category ->
        ExtraContentRow(
            Icons.Filled.Category,
            category.name,
        )
    }
    expense.place?.let { place ->
        ExtraContentRow(
            Icons.Default.Place,
            place,
        )
    }
}


@Composable
private fun ExtraContentRow(
    contentIcon: ImageVector,
    contentString: String,
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(contentIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = contentString,
                modifier = Modifier.padding(start = 4.dp, end = 2.dp)
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