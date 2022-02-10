package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.database.models.TypeOfExpense

@Composable
fun TypeOfExpenseCard(
    typeOfExpense: TypeOfExpense,
    onUpdateButtonClick: (TypeOfExpense) -> Unit,
    onDeleteButtonClick: (TypeOfExpense) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = typeOfExpense.name, modifier = Modifier.padding(start = 8.dp))

            Row {
                IconButton(onClick = { onUpdateButtonClick(typeOfExpense) }) {
                    Icon(Icons.Default.Edit, contentDescription = null);
                }

                IconButton(onClick = { onDeleteButtonClick(typeOfExpense) }) {
                    Icon(Icons.Default.Delete, contentDescription = null);
                }
            }
        }
    }

}