package com.example.expensetrackerv2.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.ui.theme.IncomeColor
import com.example.expensetrackerv2.ui.theme.ExpenseColor
import com.example.expensetrackerv2.utilities.DateUtils

@Composable
fun ExtraContentRow(contentName: String, contentIcon: ImageVector, contentString: String) {
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
                    style = MaterialTheme.typography.subtitle1,
                    text = contentName,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Text(style = MaterialTheme.typography.subtitle1, text = contentString)
        }
    }
}

@Composable
fun ExpenseCard(
    expenseWithItsType: ExpenseWithItsType,
    navController: NavController,
    onDeleteButtonClick: (ExpenseWithItsType) -> Unit
) {
    var isCardExtended by remember { mutableStateOf(false) }
    val dropDownIconRotation = if (isCardExtended) 0f else -180f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
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
                    style = MaterialTheme.typography.caption,
                    text = DateUtils.toOnlyDateString(expenseWithItsType.date)
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
                    style = MaterialTheme.typography.h5,
                    text = expenseWithItsType.title,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    style = MaterialTheme.typography.h5,
                    text = (expenseWithItsType.price * expenseWithItsType.type.multiplier).toString(),
                    color = if (expenseWithItsType.type == Type.OUTGO) ExpenseColor else IncomeColor
                )
            }

            if (isCardExtended) {
                ExtraContentRow(
                    stringResource(id = R.string.place),
                    Icons.Default.Place,
                    expenseWithItsType.place
                )
                ExtraContentRow(
                    stringResource(id = R.string.description),
                    Icons.Default.Message,
                    expenseWithItsType.description
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        navController.navigate(
                            Routes.ExpenseForm.route.plus(
                                "?EXPENSE_ID=${expenseWithItsType.id}"
                            )
                        )
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = null);
                        Text(text = stringResource(id = R.string.edit))
                    }

                    TextButton(onClick = { onDeleteButtonClick(expenseWithItsType) }) {
                        Icon(Icons.Default.Delete, contentDescription = null);
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
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