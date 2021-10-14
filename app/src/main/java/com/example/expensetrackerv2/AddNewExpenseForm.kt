package com.example.expensetrackerv2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils
import java.util.*

@Composable
fun AddNewExpenseForm() {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(Date())) }
    var place by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column {
        TextField(value = title, onValueChange = {title = it}, label = { Text("Title")}, leadingIcon = { Icon(
            Icons.Default.Title,
            contentDescription = null
        )} , modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        TextField(value = price, onValueChange = {price = it}, label = { Text("Price")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), leadingIcon = { Icon(
            Icons.Default.AttachMoney,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        TextField(value = date, onValueChange = {date = it}, label = { Text("Date")}, leadingIcon = { Icon(
            Icons.Default.Today,
            contentDescription = null
        )}, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        TextField(value = place, onValueChange = {place = it}, label = { Text("Place")}, leadingIcon = { Icon(
            Icons.Default.Place,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        TextField(value = description, onValueChange = {description = it}, label = { Text("Description")}, leadingIcon = { Icon(
            Icons.Default.Message,
            contentDescription = null
        )} ,modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
        Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewExpenseFormPreview() {
    ExpenseTrackerV2Theme {
        AddNewExpenseForm()
    }

}