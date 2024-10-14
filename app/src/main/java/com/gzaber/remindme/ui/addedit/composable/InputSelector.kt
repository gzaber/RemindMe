package com.gzaber.remindme.ui.addedit.composable

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun InputSelector(
    @StringRes title: Int,
    textValue: String,
    icon: ImageVector,
    @StringRes iconDescriptionText: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(title),
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
                contentDescription = stringResource(iconDescriptionText),
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
            title = R.string.add_edit_date_title,
            textValue = "Some title",
            icon = Icons.Default.DateRange,
            iconDescriptionText = R.string.add_edit_date_icon_description,
            onClick = {}
        )
    }
}