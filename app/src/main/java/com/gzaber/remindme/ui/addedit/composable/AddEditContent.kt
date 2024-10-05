package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun AddEditContent(
    contentPadding: PaddingValues,
    nameValue: String,
    dateValue: String,
    timeValue: String,
    advanceValue: String,
    onNameChanged: (String) -> Unit,
    onDateButtonClick: () -> Unit,
    onTimeButtonClick: () -> Unit,
    onAdvanceButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())

    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.add_edit_name_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = nameValue,
                singleLine = true,
                placeholder = { Text(text = stringResource(R.string.add_edit_name_placeholder)) },
                onValueChange = onNameChanged,
                modifier = Modifier.fillMaxWidth()
            )
        }
        InputSelector(
            title = stringResource(R.string.add_edit_date_title),
            textValue = dateValue,
            icon = Icons.Default.DateRange,
            iconDescriptionText = stringResource(R.string.add_edit_date_icon_description),
            onClick = onDateButtonClick
        )
        InputSelector(
            title = stringResource(R.string.add_edit_time_title),
            textValue = timeValue,
            icon = Icons.Default.Settings,
            iconDescriptionText = stringResource(R.string.add_edit_time_icon_description),
            onClick = onTimeButtonClick
        )
        InputSelector(
            title = stringResource(R.string.add_edit_advance_title),
            textValue = "$advanceValue ${stringResource(R.string.add_edit_advance_value_suffix)}",
            icon = Icons.Default.Notifications,
            iconDescriptionText = stringResource(R.string.add_edit_advance_icon_description),
            onClick = onAdvanceButtonClick
        )
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    RemindMeTheme {
        AddEditContent(
            contentPadding = PaddingValues(0.dp),
            nameValue = "",
            dateValue = "24-05-2010",
            timeValue = "11:00",
            advanceValue = "1 day",
            onNameChanged = {},
            onDateButtonClick = {},
            onTimeButtonClick = {},
            onAdvanceButtonClick = {}
        )
    }
}