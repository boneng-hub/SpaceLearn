package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

@Composable
fun QuizSetupRoute(
    onStartQuiz: (QuizConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: QuizSetupViewModel =
        hiltViewModel()

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    QuizSetupScreen(
        uiState = uiState,
        onTopicSelected =
            viewModel::selectTopic,
        onDifficultySelected =
            viewModel::selectDifficulty,
        onQuestionCountSelected =
            viewModel::selectQuestionCount,
        onRandomiseChanged =
            viewModel::setRandomiseQuestions,
        onStartQuiz = {
            onStartQuiz(
                uiState.toQuizConfig()
            )
        },
        modifier = modifier
    )
}
@Composable
fun QuizSetupScreen(
    uiState: QuizSetupUiState,
    onTopicSelected: (QuizTopic) -> Unit,
    onDifficultySelected:
        (Difficulty) -> Unit,
    onQuestionCountSelected: (Int) -> Unit,
    onRandomiseChanged: (Boolean) -> Unit,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement =
                Arrangement.Center,
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()

            Text(
                text = "Loading quiz preferences...",
                modifier = Modifier.padding(
                    top = 16.dp
                )
            )
        }

        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Choose Your Quiz",
                style =
                    MaterialTheme.typography
                        .headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 20.dp
                )
            )
        }

        item {
            Text(
                text = "Topic",
                style =
                    MaterialTheme.typography
                        .titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        QuizTopic.entries.forEach { topic ->
            item {
                FilterChip(
                    selected =
                        uiState.selectedTopic ==
                                topic,
                    onClick = {
                        onTopicSelected(topic)
                    },
                    label = {
                        Text(
                            text = topic.displayName
                        )
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Text(
                text = "Difficulty",
                style =
                    MaterialTheme.typography
                        .titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    Difficulty.EASY,
                    Difficulty.MEDIUM
                ).forEach { difficulty ->
                    FilterChip(
                        selected =
                            uiState
                                .selectedDifficulty ==
                                    difficulty,
                        onClick = {
                            onDifficultySelected(
                                difficulty
                            )
                        },
                        label = {
                            Text(
                                text =
                                    difficulty
                                        .displayName
                            )
                        }
                    )
                }
            }
        }

        item {
            Text(
                text = "Number of questions",
                style =
                    MaterialTheme.typography
                        .titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                listOf(3, 5).forEach { count ->
                    FilterChip(
                        selected =
                            uiState
                                .selectedQuestionCount ==
                                    count,
                        onClick = {
                            onQuestionCountSelected(
                                count
                            )
                        },
                        label = {
                            Text(
                                text =
                                    count.toString()
                            )
                        }
                    )
                }
            }
        }

        item {
            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {
                    Text(
                        text =
                            "Randomise questions",
                        style =
                            MaterialTheme.typography
                                .titleMedium
                    )

                    Text(
                        text =
                            "Change the question order for this quiz.",
                        style =
                            MaterialTheme.typography
                                .bodySmall
                    )
                }

                Switch(
                    checked =
                        uiState.randomiseQuestions,
                    onCheckedChange =
                        onRandomiseChanged
                )
            }
        }

        item {
            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(text = "Begin Quiz")
            }
        }
    }
}