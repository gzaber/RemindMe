package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun InputSelector(
    title: String,
    textValue: String,
    icon: ImageVector,
    iconDescriptionText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
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
            Icon(
                icon,
                contentDescription = iconDescriptionText,
                modifier = Modifier.padding(start = 8.dp)
            )
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
            icon = Icons.Default.DateRange,
            iconDescriptionText = "icon description",
            onClick = {}
        )
    }
}