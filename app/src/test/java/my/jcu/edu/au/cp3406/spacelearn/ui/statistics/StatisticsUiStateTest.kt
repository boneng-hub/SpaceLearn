package my.jcu.edu.au.cp3406.spacelearn.ui.statistics

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StatisticsUiStateTest {

    @Test
    fun buildStatisticsUiState_calculatesOverallStatistics() {
        val results = listOf(
            QuizResult(
                id = 1,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.EASY,
                totalQuestions = 5,
                correctAnswers = 4,
                completedAt = 1000L
            ),
            QuizResult(
                id = 2,
                topic = QuizTopic.SOLAR_SYSTEM,
                difficulty = Difficulty.EASY,
                totalQuestions = 5,
                correctAnswers = 3,
                completedAt = 2000L
            )
        )

        val state =
            buildStatisticsUiState(results)

        assertFalse(state.isLoading)
        assertTrue(state.hasResults)
        assertEquals(2, state.totalQuizzes)
        assertEquals(10, state.totalQuestions)
        assertEquals(7, state.totalCorrectAnswers)
        assertEquals(70, state.overallAccuracy)
    }

    @Test
    fun buildStatisticsUiState_groupsResultsByTopic() {
        val results = listOf(
            QuizResult(
                id = 1,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.EASY,
                totalQuestions = 5,
                correctAnswers = 4,
                completedAt = 1000L
            ),
            QuizResult(
                id = 2,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.MEDIUM,
                totalQuestions = 5,
                correctAnswers = 3,
                completedAt = 2000L
            )
        )

        val state =
            buildStatisticsUiState(results)

        assertEquals(
            1,
            state.topicPerformances.size
        )

        val planetsPerformance =
            state.topicPerformances.first()

        assertEquals(
            QuizTopic.PLANETS,
            planetsPerformance.topic
        )

        assertEquals(
            2,
            planetsPerformance.quizzesCompleted
        )

        assertEquals(
            10,
            planetsPerformance.questionsAnswered
        )

        assertEquals(
            70,
            planetsPerformance.accuracy
        )
    }

    @Test
    fun buildStatisticsUiState_withNoResults_returnsEmptyState() {
        val state =
            buildStatisticsUiState(emptyList())

        assertFalse(state.isLoading)
        assertFalse(state.hasResults)
        assertEquals(0, state.totalQuizzes)
        assertEquals(0, state.overallAccuracy)
        assertTrue(state.recentResults.isEmpty())
    }

    @Test
    fun buildStatisticsUiState_limitsRecentResultsToFive() {
        val results =
            (1L..8L).map { id ->
                QuizResult(
                    id = id,
                    topic = QuizTopic.SOLAR_SYSTEM,
                    difficulty = Difficulty.EASY,
                    totalQuestions = 3,
                    correctAnswers = 2,
                    completedAt = id * 1000
                )
            }

        val state =
            buildStatisticsUiState(results)

        assertEquals(
            5,
            state.recentResults.size
        )
    }
}