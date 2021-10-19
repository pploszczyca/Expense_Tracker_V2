package com.example.expensetrackerv2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Type
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import com.example.expensetrackerv2.utilities.DateUtils

@Composable
fun ExtraContentRow(contentName: String, contentIcon: ImageVector, contentString: String) {
    Spacer(modifier = Modifier.height(5.dp))
    if(contentString != ""){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row( verticalAlignment = Alignment.CenterVertically ) {
                Icon(contentIcon, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(style = MaterialTheme.typography.subtitle1, text = contentName, modifier = Modifier.padding(start = 5.dp))
            }
            Text(style = MaterialTheme.typography.subtitle1, text = contentString)
        }
    }
}

@Composable
fun ExpenseCard(expense: Expense, typeOfExpense: TypeOfExpense?) {
    var visible by remember { mutableStateOf(false) }
    var dropDownIconRotation = if(visible) 0f else -180f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                visible = !visible
            }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(style = MaterialTheme.typography.caption, text = DateUtils.toOnlyDateString(expense.date))
                Icon(Icons.Default.ArrowDropUp, contentDescription = null, modifier = Modifier.rotate(dropDownIconRotation))
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(style = MaterialTheme.typography.h5, text = expense.title)
                Text(style = MaterialTheme.typography.h5, text = (expense.price * typeOfExpense!!.type.multiplier).toString(), color = if(typeOfExpense.type == Type.OUTGO) Color.Red else Color.Green)
            }

            if(visible) {
                ExtraContentRow("Place:", Icons.Default.Place, expense.place)
                ExtraContentRow("Description:", Icons.Default.Message, expense.description)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerV2Theme {
        ExpenseCard(expense = Expense(title = "Zakupy w Biedronce", price = 50.0, description = "Opis", place = "Wadowice"), typeOfExpense = TypeOfExpense(name = "Zakupy", type = Type.OUTGO))
    }
}