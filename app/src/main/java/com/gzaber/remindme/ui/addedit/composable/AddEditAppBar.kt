package com.gzaber.remindme.ui.addedit.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gzaber.remindme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAppBar(
    @StringRes title: Int,
    isLoading: Boolean,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector = Icons.Default.Close,
    @StringRes navigationIconDescription: Int = R.string.add_edit_close_icon_description,
    @DrawableRes saveIcon: Int = R.drawable.save,
    @StringRes saveIconDescription: Int = R.string.add_edit_save_icon_description,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(title)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    navigationIcon,
                    contentDescription = stringResource(navigationIconDescription)
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        painterResource(saveIcon),
                        contentDescription = stringResource(saveIconDescription)
                    )
                }
            }
        }
    )
}