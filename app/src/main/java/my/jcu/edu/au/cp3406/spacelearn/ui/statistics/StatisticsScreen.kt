package my.jcu.edu.au.cp3406.spacelearn.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository

@Composable
fun StatisticsRoute(
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier
) {
    val factory = remember(progressRepository) {
        StatisticsViewModelFactory(
            progressRepository = progressRepository
        )
    }

    val viewModel: StatisticsViewModel = viewModel(
        factory = factory
    )

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    StatisticsScreen(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            LoadingStatistics(
                modifier = modifier
            )
        }

        !uiState.hasResults -> {
            EmptyStatistics(
                modifier = modifier
            )
        }

        else -> {
            StatisticsContent(
                uiState = uiState,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun LoadingStatistics(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Text(
            text = "Loading your progress...",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun EmptyStatistics(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No quiz results yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Complete your first quiz to start tracking your learning progress.",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun StatisticsContent(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Your Learning Progress",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )
        }

        item {
            OverviewCard(
                uiState = uiState
            )
        }

        item {
            Text(
                text = "Performance by Topic",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        items(
            items = uiState.topicPerformances,
            key = { performance ->
                performance.topic.name
            }
        ) { performance ->
            TopicPerformanceCard(
                performance = performance
            )
        }

        item {
            Text(
                text = "Recent Results",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        items(
            items = uiState.recentResults,
            key = { result ->
                result.id
            }
        ) { result ->
            RecentResultCard(
                result = result
            )
        }

        item {
            Column(
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun OverviewCard(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatisticRow(
                label = "Quizzes completed",
                value = uiState.totalQuizzes.toString()
            )

            StatisticRow(
                label = "Questions answered",
                value = uiState.totalQuestions.toString()
            )

            StatisticRow(
                label = "Correct answers",
                value = uiState.totalCorrectAnswers.toString()
            )

            StatisticRow(
                label = "Overall accuracy",
                value = "${uiState.overallAccuracy}%"
            )

            uiState.bestResult?.let { bestResult ->
                StatisticRow(
                    label = "Best result",
                    value =
                        "${bestResult.correctAnswers}/" +
                                "${bestResult.totalQuestions}"
                )
            }
        }
    }
}

@Composable
private fun StatisticRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {
        Text(text = label)

        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TopicPerformanceCard(
    performance: TopicPerformance,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {
                Text(
                    text = performance.topic.displayName,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${performance.accuracy}%"
                )
            }

            LinearProgressIndicator(
                progress = {
                    performance.accuracy / 100f
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text =
                    "${performance.quizzesCompleted} quizzes • " +
                            "${performance.questionsAnswered} questions",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun RecentResultCard(
    result: QuizResult,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {
                Text(
                    text = result.topic.displayName,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text =
                        "${result.correctAnswers}/" +
                                "${result.totalQuestions}"
                )
            }

            Text(
                text =
                    "${result.difficulty.displayName} • " +
                            "${result.accuracy}% accuracy",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = formatResultDate(
                    result.completedAt
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun formatResultDate(
    timestamp: Long
): String {
    val formatter = SimpleDateFormat(
        "dd MMM yyyy, HH:mm",
        Locale.getDefault()
    )

    return formatter.format(
        Date(timestamp)
    )
}