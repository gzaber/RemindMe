package com.gzaber.remindme.ui.reminders.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.theme.RemindMeTheme

@Composable
fun EmptyListInfo(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    infoIcon: ImageVector = Icons.Filled.Info,
    @StringRes infoIconContentDescription: Int = R.string.info_icon_content_description,
    @StringRes infoText: Int = R.string.empty_reminders_list_info
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            infoIcon,
            contentDescription = stringResource(infoIconContentDescription),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(infoText),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun EmptyListInfoPreview() {
    RemindMeTheme {
        EmptyListInfo(
            contentPadding = PaddingValues(16.dp)
        )
    }
}