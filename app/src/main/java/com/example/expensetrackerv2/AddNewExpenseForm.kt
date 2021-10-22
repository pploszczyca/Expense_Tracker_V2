package com.example.expensetrackerv2

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils
import java.util.*

@Composable
fun AddNewExpenseForm(navController: NavController?, context: Context?) {
    val expenseDao = AppDatabase.getInstance(context = context).expenseDao()

    val typeOfExpenseList = expenseDao.getAllTypesOfExpense()

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(typeOfExpenseList.first()) }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(Date())) }
    var place by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


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
            typeOfExpenseList.forEach { typeOfExpense ->
                RadioButton(selected = type == typeOfExpense, onClick = {
                    type = typeOfExpense
                })
                Text(text = typeOfExpense.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
        Button(onClick = {
            expenseDao.insertAllExpenses(expenses = arrayOf(Expense(title = title, date = DateUtils.stringToDate(date), price = price.toDouble(), place = place, description = description, typeOfExpenseId = type.id)))
            navController!!.navigate(Routes.Main.route)
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewExpenseFormPreview() {
    ExpenseTrackerV2Theme {
        AddNewExpenseForm(navController = null, context = null)
    }

}