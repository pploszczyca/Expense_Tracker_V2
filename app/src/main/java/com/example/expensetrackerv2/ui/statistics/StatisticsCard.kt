package com.example.expensetrackerv2.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsCard(title: String, number: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = number,
                color = color,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}