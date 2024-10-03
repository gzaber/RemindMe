package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.ui.reminders.model.UiReminder
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun RemindersContent(
    reminders: List<UiReminder>,
    onUpdateReminder: (Int) -> Unit,
    onDeleteReminder: (Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val reminderId = remember { mutableIntStateOf(0) }

    if (reminders.isEmpty()) {
        EmptyListInfo(
            contentPadding = contentPadding,
            modifier = modifier
        )
    } else {
        LazyColumn(
            contentPadding = contentPadding,
            modifier = modifier.fillMaxSize()
        ) {
            items(reminders, key = { it.id }) { reminder ->
                ReminderListItem(
                    reminder = reminder,
                    onUpdateClick = { onUpdateReminder(reminder.id) },
                    onDeleteClick = {
                        reminderId.intValue = reminder.id
                        showDeleteDialog.value = true
                    }
                )
            }
        }
    }

    if (showDeleteDialog.value) {
        DeleteReminderDialog(
            onConfirmation = {
                onDeleteReminder(reminderId.intValue)
                showDeleteDialog.value = false
            },
            onDismissRequest = { showDeleteDialog.value = false }
        )
    }
}

@Preview
@Composable
private fun RemindersContentPreview() {
    RemindMeTheme {
        RemindersContent(
            reminders = listOf(
                UiReminder(
                    id = 1,
                    name = "Do something 1",
                    color = Color.LightGray,
                    expiration = "2024-04-01 12:00"
                ),
                UiReminder(
                    id = 2,
                    name = "Do something 2",
                    color = Color.Red,
                    expiration = "2024-04-01 12:00"
                ),
                UiReminder(
                    id = 3,
                    name = "Do something 3",
                    color = Color.Green,
                    expiration = "2024-04-01 12:00"
                ),
                UiReminder(
                    id = 4,
                    name = "Do something 4",
                    color = Color.Yellow,
                    expiration = "2024-04-01 12:00"
                ),
            ),
            onUpdateReminder = {},
            onDeleteReminder = {},
            contentPadding = PaddingValues(0.dp)
        )
    }
}

@Preview
@Composable
private fun RemindersContentEmptyListPreview() {
    RemindMeTheme {
        RemindersContent(
            reminders = emptyList(),
            onUpdateReminder = {},
            onDeleteReminder = {},
            contentPadding = PaddingValues(0.dp)
        )
    }
}