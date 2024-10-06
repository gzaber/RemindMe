package com.gzaber.remindme.ui.reminders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.reminders.composable.LoadingBox
import com.gzaber.remindme.ui.reminders.composable.RemindersAppBar
import com.gzaber.remindme.ui.reminders.composable.RemindersContent
import com.gzaber.remindme.ui.reminders.composable.RemindersFloatingActionButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun RemindersScreen(
    onNavigateToAddEdit: (Int?) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: RemindersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            RemindersAppBar(
                title = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        },
        floatingActionButton = {
            RemindersFloatingActionButton(
                icon = Icons.Default.Add,
                iconDescription = stringResource(R.string.fab_content_description),
                onClick = { onNavigateToAddEdit(null) }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
    { contentPadding ->
        if (uiState.isLoading) {
            LoadingBox(contentPadding = contentPadding)
        } else {
            RemindersContent(
                reminders = uiState.reminders,
                onUpdateReminder = onNavigateToAddEdit,
                onDeleteReminder = viewModel::deleteReminder,
                contentPadding = contentPadding
            )
        }

        if (uiState.isError) {
            val errorMessage = stringResource(R.string.error_message)
            LaunchedEffect(errorMessage) {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }
}