package com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.pploszczyca.expensetrackerv2.features.main.R

@Composable
fun MainFilterDialog(
    viewModel: MainFilterDialogViewModel,
) {
    val viewState = viewModel.viewState

    Dialog(
        onDismissRequest = { viewModel.onEvent(MainFilterDialogEvent.CloseDialog) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(id = R.string.main_filter_dialog_tile),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )

                Column(
                    modifier = Modifier
                        .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.6).dp)
                        .verticalScroll(rememberScrollState())

                ) {
                    viewState.options.forEach { filterOption ->
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(align = Alignment.BottomStart),
                            onClick = {
                                viewModel.onEvent(
                                    MainFilterDialogEvent.OptionSelected(
                                        key = filterOption.key
                                    )
                                )
                            }) {
                            Text(modifier = Modifier.fillMaxWidth(), text = filterOption.dateText)
                        }
                    }
                }

                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = { viewModel.onEvent(MainFilterDialogEvent.ResetSelection) }) {
                        Text(text = stringResource(id = R.string.reset))
                    }
                    TextButton(
                        onClick = { viewModel.onEvent(MainFilterDialogEvent.CloseDialog) }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            }
        },
    )
}
