package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import my.jcu.edu.au.cp3406.spacelearn.data.local.LocalQuestionBank
import androidx.lifecycle.compose.collectAsStateWithLifecycle
class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    init {
        startQuiz()
    }

    fun startQuiz() {
        val quizQuestions = LocalQuestionBank.questions
            .take(DEFAULT_QUESTION_COUNT)

        _uiState.value = QuizUiState(
            questions = quizQuestions
        )
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
            _uiState.update { state ->
                state.copy(
                    isQuizComplete = true
                )
            }

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

    fun restartQuiz() {
        startQuiz()
    }

    private companion object {
        const val DEFAULT_QUESTION_COUNT = 5
    }
}