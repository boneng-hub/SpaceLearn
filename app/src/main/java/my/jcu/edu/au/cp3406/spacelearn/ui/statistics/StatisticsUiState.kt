package my.jcu.edu.au.cp3406.spacelearn.ui.statistics

import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

data class TopicPerformance(
    val topic: QuizTopic,
    val quizzesCompleted: Int,
    val questionsAnswered: Int,
    val correctAnswers: Int
) {
    val accuracy: Int
        get() {
            if (questionsAnswered == 0) {
                return 0
            }

            return correctAnswers * 100 / questionsAnswered
        }
}

data class StatisticsUiState(
    val isLoading: Boolean = true,
    val totalQuizzes: Int = 0,
    val totalQuestions: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val overallAccuracy: Int = 0,
    val bestResult: QuizResult? = null,
    val topicPerformances: List<TopicPerformance> = emptyList(),
    val recentResults: List<QuizResult> = emptyList()
) {
    val hasResults: Boolean
        get() = totalQuizzes > 0
}