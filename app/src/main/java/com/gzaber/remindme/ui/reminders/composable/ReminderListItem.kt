package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.ui.reminders.model.UiReminder
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun ReminderListItem(
    reminder: UiReminder,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(reminder.color)
                )
                ReminderMenuButton(
                    onUpdateClick = onUpdateClick,
                    onDeleteClick = onDeleteClick
                )
            }
            Text(
                text = reminder.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = reminder.expiration,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReminderListItemPreview() {
    RemindMeTheme {
        ReminderListItem(
            reminder = UiReminder(
                id = 1,
                name = "Do something",
                color = Color.Green,
                expiration = "2024-04-01 12:00"
            ),
            onUpdateClick = {},
            onDeleteClick = {}
        )
    }
}