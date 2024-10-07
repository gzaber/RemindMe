package com.gzaber.remindme.ui.reminders.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun DeleteReminderDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.warning,
    @StringRes iconDescription: Int = R.string.delete_reminder_dialog_icon_description,
    @StringRes title: Int = R.string.delete_reminder_dialog_title,
    @StringRes text: Int = R.string.delete_reminder_dialog_text,
    @StringRes confirmButtonText: Int = R.string.confirm_button_text,
    @StringRes dismissButtonText: Int = R.string.dismiss_button_text,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(
                painterResource(icon),
                contentDescription = stringResource(iconDescription)
            )
        },
        title = { Text(text = stringResource(title)) },
        text = { Text(text = stringResource(text)) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = stringResource(confirmButtonText))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(dismissButtonText))
            }
        }
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