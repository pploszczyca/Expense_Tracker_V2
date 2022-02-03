package com.example.expensetrackerv2.ui.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ExpenseForm(navController: NavController?, expenseID: Int? = 0) {
    val currentContext = LocalContext.current
    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()
    val expenseWithItsType =
        if (expenseID == 0) ExpenseWithItsType() else expenseDao.getExpenseWithItsType(expenseID!!)
    val expenses = expenseDao.getAllExpenses()
    val typeOfExpenseList = expenseDao.getAllTypesOfExpense()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var title by remember { mutableStateOf(expenseWithItsType.title) }
    var price by remember { mutableStateOf(if (expenseWithItsType.price == 0.0) "" else expenseWithItsType.price.toString()) }
    var type by remember { mutableStateOf(expenseWithItsType.typeID) }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(expenseWithItsType.date)) }
    var place by remember { mutableStateOf(expenseWithItsType.place) }
    var description by remember { mutableStateOf(expenseWithItsType.description) }

    val checkForm = title != "" && price != "" && price.toDouble() >= 0.0

    Column {
        AutoCompleteOutlinedTextField(
            value = title,
            onValueChange = { title = it },
            icon = Icons.Default.Title,
            label = "Title",
            suggestionsInput = expenses.map { it.title }
        )

        ExpenseFormTextField(
            value = price,
            onValueChange = { price = it },
            icon = Icons.Default.AttachMoney,
            label = "Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ExpenseFormTextField(
            value = date,
            onValueChange = { date = it },
            icon = Icons.Default.Today,
            label = "Date"
        )

        AutoCompleteOutlinedTextField(
            value = place,
            onValueChange = { place = it },
            icon = Icons.Default.Place,
            label = "Place",
            suggestionsInput = expenses.map { it.place }
        )

        ExpenseFormTextField(
            value = description,
            onValueChange = { description = it },
            icon = Icons.Default.Message,
            label = "Description",
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Row(
            modifier = Modifier
                .selectableGroup()
                .padding(5.dp)
                .padding(start = 10.dp)
        ) {
            typeOfExpenseList.forEach { typeOfExpense ->
                RadioButton(selected = type == typeOfExpense.id, onClick = {
                    type = typeOfExpense.id
                })
                Text(text = typeOfExpense.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp)
        )

        Button(onClick = {
            if (checkForm) {
                runBlocking {
                    val expense = Expense(
                        id = expenseWithItsType.id,
                        title = title,
                        date = DateUtils.stringToDate(date),
                        price = price.toDouble(),
                        place = place,
                        description = description,
                        typeOfExpenseId = type,
                    )

                    // if id = 0 that means is new expense
                    if (expense.id == 0) expenseDao.insertAllExpenses(expense) else expenseDao.updateExpense(
                        expense
                    )
                }
                navController!!.popBackStack()
                navController.navigate(Routes.Main.route)
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(message = "Data is incorrect")
                }
            }

        }, modifier = Modifier.padding(20.dp)) {
            Text(text = if (expenseWithItsType.id == 0) "Add" else "Update")
        }
    }
}
