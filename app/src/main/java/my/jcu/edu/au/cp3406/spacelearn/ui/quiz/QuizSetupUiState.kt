package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

data class QuizSetupUiState(
    val selectedTopic: QuizTopic =
        QuizTopic.SOLAR_SYSTEM,
    val selectedDifficulty: Difficulty =
        Difficulty.EASY,
    val selectedQuestionCount: Int = 3,
    val randomiseQuestions: Boolean = false,
    val isLoading: Boolean = true
) {
    fun toQuizConfig(): QuizConfig {
        return QuizConfig(
            topic = selectedTopic,
            difficulty = selectedDifficulty,
            questionCount = selectedQuestionCount,
            randomiseQuestions = randomiseQuestions
        )
    }
}