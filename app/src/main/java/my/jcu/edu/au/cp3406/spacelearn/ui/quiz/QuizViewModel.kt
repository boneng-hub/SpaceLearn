package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import my.jcu.edu.au.cp3406.spacelearn.data.local.LocalQuestionBank
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
class QuizViewModel : ViewModel() {

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

        val matchingQuestions =
            LocalQuestionBank.questions.filter { question ->
                question.topic == config.topic &&
                        question.difficulty == config.difficulty
            }

        val selectedQuestions =
            matchingQuestions.take(config.questionCount)

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
}

