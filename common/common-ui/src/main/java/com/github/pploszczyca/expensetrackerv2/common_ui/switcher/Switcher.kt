package com.github.pploszczyca.expensetrackerv2.common_ui.switcher

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Switcher(
    value: SwitchValue,
    onText: String,
    offText: String,
    title: String? = null,
    onValueChange: (SwitchValue) -> Unit,
) {
    Row(
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 16.dp)
            ) }
        Row(
            Modifier
                .weight(1f)
                .background(Color.Transparent, RoundedCornerShape(8.dp))
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Text(
                text = onText,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onValueChange(SwitchValue.On) }
                    .background(
                        if (value == SwitchValue.On) Color.Green else Color.Transparent,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = if (value == SwitchValue.On) Color.White else LocalContentColor.current,
                textAlign = TextAlign.Center
            )
            Text(
                text = offText,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onValueChange(SwitchValue.Off) }
                    .background(
                        if (value == SwitchValue.Off) Color.Red else Color.Transparent,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = if (value == SwitchValue.Off) Color.White else LocalContentColor.current,
                textAlign = TextAlign.Center
            )
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

