package com.gzaber.remindme.ui.addedit.composable

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.gzaber.remindme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes confirmButtonText: Int = R.string.confirm_button_text,
    @StringRes dismissButtonText: Int = R.string.dismiss_button_text,
) {
    val timePickerModalDescription = stringResource(R.string.time_picker_modal_content_description)
    val timePickerDescription = stringResource(R.string.time_picker_content_description)
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )


    AlertDialog(
        modifier = modifier.semantics { contentDescription = timePickerModalDescription },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(timePickerState.hour, timePickerState.minute)
                onDismiss()
            }) {
                Text(text = stringResource(confirmButtonText))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(dismissButtonText))
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.semantics { contentDescription = timePickerDescription })
        }
    )
}