package com.example.expensetrackerv2.ui.category_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetrackerv2.database.models.Category

@Composable
fun CategoryCard(
    category: Category,
    onUpdateButtonClick: (Category) -> Unit,
    onDeleteButtonClick: (Category) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = category.name, modifier = Modifier.padding(start = 8.dp))

            Row {
                IconButton(onClick = { onUpdateButtonClick(category) }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }

                IconButton(onClick = { onDeleteButtonClick(category) }) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }

}