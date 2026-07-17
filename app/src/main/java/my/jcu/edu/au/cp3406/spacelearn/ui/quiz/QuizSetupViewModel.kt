package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

class QuizSetupViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(QuizSetupUiState())

    val uiState: StateFlow<QuizSetupUiState> =
        _uiState.asStateFlow()

    init {
        observeDefaultSettings()
    }

    private fun observeDefaultSettings() {
        viewModelScope.launch {
            settingsRepository.settings.collectLatest {
                    settings ->

                _uiState.update { currentState ->
                    currentState.copy(
                        selectedDifficulty =
                            settings.defaultDifficulty,
                        selectedQuestionCount =
                            settings.defaultQuestionCount,
                        randomiseQuestions =
                            settings.randomiseQuestions,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun selectTopic(topic: QuizTopic) {
        _uiState.update { state ->
            state.copy(
                selectedTopic = topic
            )
        }
    }

    fun selectDifficulty(
        difficulty: Difficulty
    ) {
        _uiState.update { state ->
            state.copy(
                selectedDifficulty = difficulty
            )
        }
    }

    fun selectQuestionCount(
        questionCount: Int
    ) {
        if (
            questionCount != 3 &&
            questionCount != 5
        ) {
            return
        }

        _uiState.update { state ->
            state.copy(
                selectedQuestionCount =
                    questionCount
            )
        }
    }

    fun setRandomiseQuestions(
        enabled: Boolean
    ) {
        _uiState.update { state ->
            state.copy(
                randomiseQuestions = enabled
            )
        }
    }
}