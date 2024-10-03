package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gzaber.remindme.R

@Composable
fun ReminderMenuButton(
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { isExpanded.value = true }
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.reminder_menu_button_icon_description)
            )
        }
        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.reminder_menu_button_update_option)) },
                onClick = {
                    onUpdateClick()
                    isExpanded.value = false
                }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(stringResource(R.string.reminder_menu_button_delete_option)) },
                onClick = {
                    onDeleteClick()
                    isExpanded.value = false
                }
            )
        }
    }
}