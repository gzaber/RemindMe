package com.gzaber.remindme.ui.reminders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.ui.reminders.composable.DeleteReminderDialog
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
            RemindersAppBar(title = R.string.app_name)
        },
        floatingActionButton = {
            RemindersFloatingActionButton(
                onClick = { onNavigateToAddEdit(null) }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
    { contentPadding ->
        if (uiState.isLoading) {
            LoadingBox(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding
            )
        } else {
            RemindersContent(
                modifier = Modifier.fillMaxSize(),
                reminders = uiState.reminders,
                contentPadding = contentPadding,
                listBottomPadding = 0.dp,
                onUpdateReminder = onNavigateToAddEdit,
                onDeleteReminder = {
                    viewModel.onReminderIdChanged(it)
                    viewModel.toggleShowDeleteDialog()
                }
            )
        }

        if (uiState.showDeleteDialog) {
            DeleteReminderDialog(
                onConfirmation = {
                    viewModel.deleteReminder()
                    viewModel.toggleShowDeleteDialog()
                },
                onDismissRequest = viewModel::toggleShowDeleteDialog
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