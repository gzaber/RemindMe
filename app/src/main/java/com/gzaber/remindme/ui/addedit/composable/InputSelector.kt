package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun InputSelector(
    title: String,
    textValue: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit),
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = textValue,
                color = Color.DarkGray,
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                            MaterialTheme.colorScheme.secondary
                        ),
                        shape = OutlinedTextFieldDefaults.shape
                    )
                    .padding(16.dp)
                    .weight(1f)
            )
            trailing()
        }
    }
}

@Preview
@Composable
private fun InputSelectorPreview() {
    RemindMeTheme {
        InputSelector(
            title = "Title",
            textValue = "Some title",
            trailing = {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Icon content description"
                    )
                }
            }
        )
    }
}