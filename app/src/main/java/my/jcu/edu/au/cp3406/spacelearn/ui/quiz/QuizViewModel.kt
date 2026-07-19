package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import java.security.Principal
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository:
    QuizRepository,
    private val progressRepository:
    ProgressRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(QuizUiState())

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()


    fun startQuiz(config: QuizConfig) {
        val currentState = _uiState.value

        if (
            currentState.config == config &&
            currentState.questions.isNotEmpty()
        ) {
            return
        }

        val selectedQuestions =
            quizRepository.getQuestions(config)

        val message = when {
            selectedQuestions.isEmpty() ->
                "No questions are available for this quiz."

            selectedQuestions.size < config.questionCount ->
                "Only ${selectedQuestions.size} questions " +
                        "are available for this selection."

            else -> null
        }

        _uiState.value = QuizUiState(
            config = config,
            questions = selectedQuestions,
            message = message
        )
    }
    private fun saveResultAndCompleteQuiz() {
        val currentState = _uiState.value
        val config = currentState.config ?: return

        if (currentState.isSavingResult) {
            return
        }

        _uiState.update { state ->
            state.copy(
                isSavingResult = true
            )
        }

        viewModelScope.launch {
            progressRepository.saveQuizResult(
                QuizResult(
                    topic = config.topic,
                    difficulty = config.difficulty,
                    totalQuestions =
                        currentState.questions.size,
                    correctAnswers =
                        currentState.score,
                    completedAt =
                        System.currentTimeMillis()
                )
            )

            _uiState.update { state ->
                state.copy(
                    isSavingResult = false,
                    isQuizComplete = true
                )
            }
        }
    }
    fun selectAnswer(answerIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.currentQuestion ?: return

        if (currentState.isQuizComplete) {
            return
        }

        if (currentState.hasAnswered) {
            return
        }

        if (answerIndex !in currentQuestion.options.indices) {
            return
        }

        val additionalScore =
            if (answerIndex == currentQuestion.correctAnswerIndex) {
                1
            } else {
                0
            }

        _uiState.update { state ->
            state.copy(
                selectedAnswerIndex = answerIndex,
                score = state.score + additionalScore
            )
        }
    }

    fun moveToNextQuestion() {
        val currentState = _uiState.value

        if (!currentState.hasAnswered) {
            return
        }

        val isLastQuestion =
            currentState.currentQuestionIndex ==
                    currentState.questions.lastIndex

        if (isLastQuestion) {
            saveResultAndCompleteQuiz()
            return
        }

        _uiState.update { state ->
            state.copy(
                currentQuestionIndex =
                    state.currentQuestionIndex + 1,
                selectedAnswerIndex = null
            )
        }
    }
}

