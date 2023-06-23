package com.example.expensetrackerv2.ui.main.features.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.extensions.toFormattedString
import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.navigation.Routes
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.ui.theme.IncomeColor

@Composable
fun ExpenseCard(
    expenseWithCategory: ExpenseWithCategory,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithCategory) -> Unit,
) {
    var isCardExtended by remember { mutableStateOf(false) }

    val dropDownIconRotation by animateFloatAsState(
        targetValue = if (isCardExtended) 0f else -180f,
        animationSpec = tween(250)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .animateContentSize()
            .clickable {
                isCardExtended = !isCardExtended
            }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = expenseWithCategory.date.toFormattedString()
                )
                Icon(
                    Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.rotate(dropDownIconRotation)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = expenseWithCategory.title,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = (expenseWithCategory.price * expenseWithCategory.categoryType.multiplier).toString(),
                    color = if (expenseWithCategory.categoryType == CategoryType.OUTGO) ExpenseColor else IncomeColor
                )
            }

            if (isCardExtended) {
                ExtraContentExpenseCard(expenseWithCategory, navController, onDeleteButtonClick)
            }
        }
    }
}

@Composable
private fun ExtraContentExpenseCard(
    expenseWithCategory: ExpenseWithCategory,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithCategory) -> Unit,
) {
    ExtraContentRow(
        stringResource(id = R.string.place),
        Icons.Default.Place,
        expenseWithCategory.place
    )
    ExtraContentRow(
        stringResource(id = R.string.description),
        Icons.Default.Message,
        expenseWithCategory.description
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = {
            navController.navigate(
                Routes.ExpenseForm.route.plus(
                    "?EXPENSE_ID=${expenseWithCategory.id}"
                )
            )
        }) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Text(text = stringResource(id = R.string.edit))
        }

        TextButton(onClick = { onDeleteButtonClick(expenseWithCategory) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
            Text(text = stringResource(id = R.string.delete))
        }
    }
}


@Composable
private fun ExtraContentRow(contentName: String, contentIcon: ImageVector, contentString: String) {
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
                    style = MaterialTheme.typography.bodySmall,
                    text = contentName,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Text(style = MaterialTheme.typography.bodySmall, text = contentString)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerV2Theme {
//        ExpenseCard(expense = Expense(title = "Zakupy w Biedronce", price = 50.0, description = "Opis", place = "Wadowice"), typeOfExpense = TypeOfExpense(name = "Zakupy", type = Type.OUTGO))
    }
}