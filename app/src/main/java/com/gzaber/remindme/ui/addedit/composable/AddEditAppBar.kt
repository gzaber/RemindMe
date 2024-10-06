package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAppBar(
    title: String,
    color: Color,
    isLoading: Boolean,
    navigationIcon: ImageVector,
    navigationIconDescription: String,
    saveIcon: ImageVector,
    saveIconDescription: String,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color
        ),
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    navigationIcon,
                    contentDescription = navigationIconDescription
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        saveIcon,
                        contentDescription = saveIconDescription
                    )
                }
            }
        }
    )
}