package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RemindersFloatingActionButton(
    icon: ImageVector,
    iconDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription
        )
    }
}