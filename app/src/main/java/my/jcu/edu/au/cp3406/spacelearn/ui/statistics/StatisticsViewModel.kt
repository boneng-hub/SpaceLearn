package my.jcu.edu.au.cp3406.spacelearn.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    progressRepository: ProgressRepository
) : ViewModel()
{
    val uiState: StateFlow<StatisticsUiState> =
        progressRepository
            .observeQuizResults()
            .map(::buildStatisticsUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5_000
                ),
                initialValue = StatisticsUiState()
            )
}

internal fun buildStatisticsUiState(
    results: List<QuizResult>
): StatisticsUiState {
    val totalQuestions =
        results.sumOf { result ->
            result.totalQuestions
        }

    val totalCorrectAnswers =
        results.sumOf { result ->
            result.correctAnswers
        }

    val overallAccuracy =
        if (totalQuestions == 0) {
            0
        } else {
            totalCorrectAnswers * 100 / totalQuestions
        }

    val bestResult =
        results.maxWithOrNull(
            compareBy<QuizResult> {
                it.accuracy
            }.thenBy {
                it.correctAnswers
            }
        )

    val topicPerformances =
        QuizTopic.entries.mapNotNull { topic ->
            val topicResults =
                results.filter { result ->
                    result.topic == topic
                }

            if (topicResults.isEmpty()) {
                return@mapNotNull null
            }

            TopicPerformance(
                topic = topic,
                quizzesCompleted = topicResults.size,
                questionsAnswered = topicResults.sumOf {
                    it.totalQuestions
                },
                correctAnswers = topicResults.sumOf {
                    it.correctAnswers
                }
            )
        }

    return StatisticsUiState(
        isLoading = false,
        totalQuizzes = results.size,
        totalQuestions = totalQuestions,
        totalCorrectAnswers = totalCorrectAnswers,
        overallAccuracy = overallAccuracy,
        bestResult = bestResult,
        topicPerformances = topicPerformances,
        recentResults = results.take(5)
    )
}