package com.github.pploszczyca.expensetrackerv2.common_ui.switcher

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.ExpenseColor
import com.github.pploszczyca.expensetrackerv2.common_ui.theme.IncomeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Switcher(
    value: SwitchValue,
    onText: String,
    offText: String,
    title: String? = null,
    onValueChange: (SwitchValue) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            SegmentedButton(
                selected = value == SwitchValue.Off,
                onClick = { onValueChange(SwitchValue.Off) },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = ExpenseColor.copy(alpha = 0.18f),
                    activeContentColor = ExpenseColor,
                    activeBorderColor = ExpenseColor,
                ),
            ) {
                Text(text = offText, style = MaterialTheme.typography.titleSmall)
            }
            SegmentedButton(
                selected = value == SwitchValue.On,
                onClick = { onValueChange(SwitchValue.On) },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = IncomeColor.copy(alpha = 0.18f),
                    activeContentColor = IncomeColor,
                    activeBorderColor = IncomeColor,
                ),
            ) {
                Text(text = onText, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}


sealed interface SwitchValue {
    data object On : SwitchValue
    data object Off : SwitchValue
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SwitcherOnPreview() {
    MaterialTheme {
        Switcher(
            value = SwitchValue.On,
            onText = "On",
            offText = "Off",
            onValueChange = {}
        )
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SwitcherOffPreview() {
    MaterialTheme {
        Switcher(
            value = SwitchValue.Off,
            onText = "On",
            offText = "Off",
            onValueChange = {}
        )
    }
}

