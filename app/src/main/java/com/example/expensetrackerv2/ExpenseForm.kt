package com.example.expensetrackerv2

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ExpenseForm(navController: NavController?, expenseID: Int? = 0) {
    val expenseDao = AppDatabase.getInstance(context = LocalContext.current).expenseDao()
    val expense = if (expenseID == 0) Expense() else expenseDao.getExpense(expenseID!!)

    val typeOfExpenseMap = expenseDao.getAllTypesOfExpenseAsMapWithIdKey()

    var title by remember { mutableStateOf(expense.title) }
    var price by remember { mutableStateOf(if(expense.price == 0.0) "" else expense.price.toString()) }
    var type by remember { mutableStateOf(typeOfExpenseMap[expense.typeOfExpenseId]) }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(expense.date)) }
    var place by remember { mutableStateOf(expense.place) }
    var description by remember { mutableStateOf(expense.description) }


    Column {
        OutlinedTextField(value = title, onValueChange = {title = it}, label = { Text("Title")}, leadingIcon = { Icon(
            Icons.Default.Title,
            contentDescription = null
        )} , modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        OutlinedTextField(value = price, onValueChange = {price = it}, label = { Text("Price")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), leadingIcon = { Icon(
            Icons.Default.AttachMoney,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        OutlinedTextField(value = date, onValueChange = {date = it}, label = { Text("Date")}, leadingIcon = { Icon(
            Icons.Default.Today,
            contentDescription = null
        )}, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        OutlinedTextField(value = place, onValueChange = {place = it}, label = { Text("Place")}, leadingIcon = { Icon(
            Icons.Default.Place,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        OutlinedTextField(value = description, onValueChange = {description = it}, label = { Text("Description")}, leadingIcon = { Icon(
            Icons.Default.Message,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        Row(modifier = Modifier.selectableGroup().padding(5.dp)) {
            typeOfExpenseMap.forEach { typeOfExpense ->
                RadioButton(selected = type == typeOfExpense.value, onClick = {
                    type = typeOfExpense.value
                })
                Text(text = typeOfExpense.value.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
        Button(onClick = {
            runBlocking {
                launch {
                    expense.title = title
                    expense.date = DateUtils.stringToDate(date)
                    expense.price = price.toDouble()
                    expense.place = place
                    expense.description = description
                    expense.typeOfExpenseId = type!!.id

                    // if id = 0 that means is new expense
                    if(expense.id == 0)  expenseDao.insertAllExpenses(expense) else expenseDao.updateExpense(expense)
                }
            }
            navController!!.navigate(Routes.Main.route)
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = if(expense.id == 0) "Add" else "Update")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewExpenseFormPreview() {
    ExpenseTrackerV2Theme {
        ExpenseForm(navController = null)
    }

}