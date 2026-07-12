package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val score: Int = 0,
    val isQuizComplete: Boolean = false
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    val hasAnswered: Boolean
        get() = selectedAnswerIndex != null

    val isCurrentAnswerCorrect: Boolean?
        get() {
            val selectedIndex = selectedAnswerIndex ?: return null
            val question = currentQuestion ?: return null

            return selectedIndex == question.correctAnswerIndex
        }

    val progress: Float
        get() {
            if (questions.isEmpty()) {
                return 0f
            }

            return (currentQuestionIndex + 1).toFloat() / questions.size
        }
}