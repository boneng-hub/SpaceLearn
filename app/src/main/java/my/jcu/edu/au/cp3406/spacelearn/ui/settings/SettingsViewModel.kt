package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository:
    SettingsRepository,
    private val progressRepository:
    ProgressRepository
) : ViewModel(){

    private val isClearingHistory =
        MutableStateFlow(false)

    val uiState: StateFlow<SettingsUiState> =
        combine(
            settingsRepository.settings,
            isClearingHistory
        ) { settings, clearingHistory ->
            SettingsUiState(
                settings = settings,
                isLoading = false,
                isClearingHistory = clearingHistory
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5_000
            ),
            initialValue = SettingsUiState()
        )

    fun updateDefaultDifficulty(
        difficulty: Difficulty
    ) {
        viewModelScope.launch {
            settingsRepository.setDefaultDifficulty(
                difficulty
            )
        }
    }

    fun updateDefaultQuestionCount(
        questionCount: Int
    ) {
        viewModelScope.launch {
            settingsRepository.setDefaultQuestionCount(
                questionCount
            )
        }
    }

    fun updateRandomiseQuestions(
        enabled: Boolean
    ) {
        viewModelScope.launch {
            settingsRepository.setRandomiseQuestions(
                enabled
            )
        }
    }

    fun clearQuizHistory() {
        if (isClearingHistory.value) {
            return
        }

        viewModelScope.launch {
            isClearingHistory.value = true

            try {
                progressRepository.clearQuizResults()
            } finally {
                isClearingHistory.value = false
            }
        }
    }
}