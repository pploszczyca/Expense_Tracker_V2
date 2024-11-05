package com.github.pploszczyca.expensetrackerv2.common_ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun Dialog(
    isDialogVisible: Boolean,
    title: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        isDialogVisible = isDialogVisible,
        title = title,
        text = { Text(text) },
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}

@Composable
fun Dialog(
    isDialogVisible: Boolean,
    title: String,
    text: @Composable () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (isDialogVisible) {
        AlertDialog(onDismissRequest = onDismiss,
            title = { Text(title) },
            text = text,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissButtonText)
                }
            }
        )
    }
}
