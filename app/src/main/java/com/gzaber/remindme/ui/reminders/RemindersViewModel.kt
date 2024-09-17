package com.gzaber.remindme.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.ui.reminders.model.UiReminder
import com.gzaber.remindme.ui.reminders.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RemindersUiState(
    val reminders: List<UiReminder> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

class RemindersViewModel(
    private val remindersRepository: RemindersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemindersUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            remindersRepository.observeAll()
                .catch { _ ->
                    _uiState.update {
                        it.copy(isError = true)
                    }
                }
                .collect { reminders ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            reminders = reminders.map { reminder -> reminder.toUiModel() }
                        )
                    }
                }
        }
    }
}