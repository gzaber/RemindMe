package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun DeleteReminderDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Info, contentDescription = "")
        },
        title = { Text(text = stringResource(R.string.delete_reminder_dialog_title)) },
        text = { Text(text = stringResource(R.string.delete_reminder_dialog_text)) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = stringResource(R.string.confirm_button_text))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.dismiss_button_text))
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun DeleteReminderDialogPreview() {
    RemindMeTheme {
        DeleteReminderDialog(
            onConfirmation = {},
            onDismissRequest = {}
        )
    }
}