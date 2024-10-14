package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
import com.gzaber.remindme.ui.reminders.model.UiReminder
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun RemindersContent(
    reminders: List<UiReminder>,
    contentPadding: PaddingValues,
    listBottomPadding: Dp,
    onUpdateReminder: (Int) -> Unit,
    onDeleteReminder: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (reminders.isEmpty()) {
        EmptyListInfo(
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = listBottomPadding),
            contentPadding = contentPadding
        ) {
            items(reminders, key = { it.id }) { reminder ->
                ReminderListItem(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    name = reminder.name,
                    expiration = reminder.formattedExpiration,
                    color = when (reminder.expirationStatus) {
                        ExpirationStatus.EXPIRED -> Color(0xFF9E9E9E)
                        ExpirationStatus.WITHIN_DAY -> Color(0xFFF44336)
                        ExpirationStatus.WITHIN_WEEK -> Color(0xFFFF9800)
                        ExpirationStatus.MORE -> Color(0xFF4CAF50)
                    },
                    isExpired = reminder.expirationStatus == ExpirationStatus.EXPIRED,
                    onUpdateClick = { onUpdateReminder(reminder.id) },
                    onDeleteClick = { onDeleteReminder(reminder.id) }
                )
                HorizontalDivider()
            }
        }
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
            contentPadding = PaddingValues(0.dp),
            listBottomPadding = 0.dp
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
            contentPadding = PaddingValues(0.dp),
            listBottomPadding = 0.dp
        )
    }
}