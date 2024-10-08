package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
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
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            items(reminders, key = { it.id }) { reminder ->
                ReminderListItem(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    name = reminder.name,
                    expiration = reminder.formattedExpiration,
                    color = when (reminder.expirationStatus) {
                        ExpirationStatus.EXPIRED -> Color.DarkGray
                        ExpirationStatus.WITHIN_DAY -> Color.Red
                        ExpirationStatus.WITHIN_WEEK -> Color.Yellow
                        ExpirationStatus.MORE -> Color.Green
                    },
                    isExpired = reminder.expirationStatus == ExpirationStatus.EXPIRED,
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
                    formattedExpiration = "2024-04-01 12:00",
                    expirationStatus = ExpirationStatus.EXPIRED
                ),
                UiReminder(
                    id = 2,
                    name = "Do something 2",
                    formattedExpiration = "2024-04-01 12:00",
                    expirationStatus = ExpirationStatus.WITHIN_DAY
                ),
                UiReminder(
                    id = 3,
                    name = "Do something 3",
                    formattedExpiration = "2024-04-01 12:00",
                    expirationStatus = ExpirationStatus.WITHIN_WEEK
                ),
                UiReminder(
                    id = 4,
                    name = "Do something 4",
                    formattedExpiration = "2024-04-01 12:00",
                    expirationStatus = ExpirationStatus.MORE
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