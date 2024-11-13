package com.gzaber.remindme.ui.reminders.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R

@Composable
fun LoadingIndicator(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 64.dp,
    @StringRes indicatorDescription: Int = R.string.loading_indicator_content_description
) {
    val indicatorContentDescription = stringResource(indicatorDescription)
    Box(
        modifier = modifier
            .padding(contentPadding)
            .semantics { contentDescription = indicatorContentDescription },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize)
        )
    }
}