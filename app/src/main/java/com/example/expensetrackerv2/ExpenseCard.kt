package com.example.expensetrackerv2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ExtraContentRow(contentName: String, contentIcon: ImageVector, contentString: String) {
    Spacer(modifier = Modifier.height(5.dp))
    if (contentString != "") {
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
fun ExpenseCard(expenseWithItsType: ExpenseWithItsType, navController: NavController) {
    var isCardExtended by remember { mutableStateOf(false) }
    val dropDownIconRotation = if (isCardExtended) 0f else -180f
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    var isCardHidden by remember { mutableStateOf(false) }
    val expenseDao = AppDatabase.getInstance(LocalContext.current).expenseDao()

    if (!isCardHidden) {
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
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        style = MaterialTheme.typography.h5,
                        text = (expenseWithItsType.price * expenseWithItsType.type.multiplier).toString(),
                        color = if (expenseWithItsType.type == Type.OUTGO) Color.Red else Color.Green
                    )
                }

                if (isCardExtended) {
                    ExtraContentRow("Place:", Icons.Default.Place, expenseWithItsType.place)
                    ExtraContentRow("Description:", Icons.Default.Message, expenseWithItsType.description)
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
                            Text(text = "Edit")
                        }

                        TextButton(onClick = { isDeleteDialogOpen = true }) {
                            Icon(Icons.Default.Delete, contentDescription = null);
                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
    }

    if (isDeleteDialogOpen) {
        AlertDialog(onDismissRequest = { isDeleteDialogOpen = false },
            title = { Text("Delete expense") },
            text = { Text("Do you want to delete it?") },
            confirmButton = {
                TextButton(onClick = {
                    runBlocking {
                        launch {
                            expenseDao.deleteExpense(expenseWithItsType)
                        }
                    }
                    isDeleteDialogOpen = false
                    isCardHidden = true
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isDeleteDialogOpen = false
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerV2Theme {
//        ExpenseCard(expense = Expense(title = "Zakupy w Biedronce", price = 50.0, description = "Opis", place = "Wadowice"), typeOfExpense = TypeOfExpense(name = "Zakupy", type = Type.OUTGO))
    }
}