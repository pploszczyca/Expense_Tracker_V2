package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.features.main.R
import java.util.Date

@Composable
fun ExpenseCard(
    expense: Expense,
    modifier: Modifier = Modifier,
    onDeleteButtonClick: (Expense) -> Unit = {},
    onEditExpenseButtonClicked: (Expense) -> Unit = {},
) {
    val expenseColor = Color(0xffff1744)
    val incomeColor = Color(0xff76ff03)
    var isCardExtended by remember { mutableStateOf(false) }

    val dropDownIconRotation by animateFloatAsState(
        targetValue = if (isCardExtended) 0f else -180f,
        animationSpec = tween(250)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .animateContentSize()
            .clickable {
                isCardExtended = !isCardExtended
            }
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
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = (expense.price * expense.category.type.multiplier).toString(),
                        color = if (expense.category.type == Category.Type.OUTGO) expenseColor else incomeColor
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Icon(
                        Icons.Default.ArrowDropUp,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(dropDownIconRotation)
                            .size(22.dp)
                    )
                }

            }

            if (isCardExtended) {
                ExtraContentExpenseCard(
                    expense = expense,
                    onDeleteButtonClick = onDeleteButtonClick,
                    onEditExpenseButtonClicked = onEditExpenseButtonClicked,
                )
            }
        }
    }
}

@Composable
private fun ExtraContentExpenseCard(
    expense: Expense,
    onDeleteButtonClick: (Expense) -> Unit = {},
    onEditExpenseButtonClicked: (Expense) -> Unit = {},
) {
    ExtraContentRow(
        stringResource(id = R.string.place),
        Icons.Default.Place,
        expense.place
    )
    ExtraContentRow(
        stringResource(id = R.string.description),
        Icons.Default.Message,
        expense.description
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = {
            onEditExpenseButtonClicked(expense)
        }) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Text(text = stringResource(id = R.string.edit))
        }

        TextButton(onClick = { onDeleteButtonClick(expense) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
            Text(text = stringResource(id = R.string.delete))
        }
    }
}


@Composable
private fun ExtraContentRow(
    contentName: String,
    contentIcon: ImageVector,
    contentString: String,
) {
    Spacer(modifier = Modifier.height(5.dp))
    if (contentString.isNotEmpty()) {
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
                    text = contentName,
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp)
                )
            }
            Text(style = MaterialTheme.typography.bodyMedium, text = contentString)
        }
    }
}

@Preview
@Composable
fun ExpenseCardPreview() {
    val category = Category(
        name = "Category name",
        type = Category.Type.INCOME,
    )
    val expense = Expense(
        title = "Expense title",
        price = 50.0,
        date = Date(),
        description = "Some description",
        place = "Place",
        category = category
    )

    ExpenseCard(expense = expense)
}

@Preview
@Composable
fun ExpenseCardWithLongTitlePreview() {
    val category = Category(
        name = "Category name",
        type = Category.Type.INCOME,
    )
    val expense = Expense(
        title = "Long long long long long long long long title",
        price = 50.0,
        date = Date(),
        description = "Some description",
        place = "Place",
        category = category
    )

    ExpenseCard(expense = expense)
}


@Preview
@Composable
fun ExtraContentRowPreview() {
    Surface {
        ExtraContentRow(
            contentName = "Place",
            contentIcon = Icons.Default.Place,
            contentString = "Biedronka",
        )
    }
}