package com.gzaber.remindme.ui.addedit

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.gzaber.remindme.ui.addedit.composable.AddEditAppBar
import com.gzaber.remindme.ui.addedit.composable.AddEditContent
import com.gzaber.remindme.ui.addedit.composable.AdvancePickerModal
import com.gzaber.remindme.ui.addedit.composable.DatePickerModal
import com.gzaber.remindme.ui.addedit.composable.TimePickerModal
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditScreen(
    @StringRes title: Int,
    onNavigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: AddEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AddEditAppBar(
                title = stringResource(title),
                color = MaterialTheme.colorScheme.primaryContainer,
                isLoading = uiState.isLoading,
                navigationIcon = Icons.Default.Close,
                navigationIconDescription = stringResource(R.string.add_edit_close_icon_description),
                saveIcon = Icons.Default.Check,
                saveIconDescription = stringResource(R.string.add_edit_save_icon_description),
                onNavigateBack = onNavigateBack,
                onSave = viewModel::saveReminder
            )
        },
    )
    { contentPadding ->
        AddEditContent(
            contentPadding = contentPadding,
            nameValue = uiState.name,
            dateValue = uiState.formattedDate,
            timeValue = uiState.formattedTime,
            advanceValue = uiState.formattedAdvance,
            onNameChanged = viewModel::onNameChanged,
            onDateButtonClick = viewModel::toggleShowingDatePicker,
            onTimeButtonClick = viewModel::toggleShowingTimePicker,
            onAdvanceButtonClick = viewModel::toggleShowingAdvancePicker
        )

        if (uiState.showDatePicker) {
            DatePickerModal(
                confirmButtonText = stringResource(R.string.confirm_button_text),
                dismissButtonText = stringResource(R.string.dismiss_button_text),
                initialDateMillis = uiState.expirationDateMillis,
                onDateSelected = viewModel::onDateChanged,
                onDismiss = viewModel::toggleShowingDatePicker
            )
        }

        if (uiState.showTimePicker) {
            TimePickerModal(
                confirmButtonText = stringResource(R.string.confirm_button_text),
                dismissButtonText = stringResource(R.string.dismiss_button_text),
                initialHour = uiState.expirationHour,
                initialMinute = uiState.expirationMinute,
                onConfirm = viewModel::onTimeChanged,
                onDismiss = viewModel::toggleShowingTimePicker
            )
        }

        if (uiState.showAdvancePicker) {
            AdvancePickerModal(
                title = stringResource(R.string.add_edit_advance_title),
                advanceUnits = viewModel.advanceUnits.map { it.toString() },
                advanceValues = viewModel.advanceValues,
                selectedAdvanceUnit = uiState.advanceUnit.toString(),
                selectedAdvanceValue = uiState.advanceValue,
                onAdvanceValueSelected = viewModel::onAdvanceValueChanged,
                onAdvanceUnitSelected = viewModel::onAdvanceUnitChanged,
                onDismiss = viewModel::toggleShowingAdvancePicker
            )
        }

        if (uiState.isSaved) {
            LaunchedEffect(uiState) {
                onNavigateBack()
            }
        }

        if (uiState.isError) {
            val errorMessage = stringResource(R.string.error_message)
            LaunchedEffect(errorMessage) {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }
}