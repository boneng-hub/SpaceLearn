package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

@Composable
fun QuizSetupScreen(
    onStartQuiz: (QuizConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTopicName by rememberSaveable {
        mutableStateOf(QuizTopic.SOLAR_SYSTEM.name)
    }

    var selectedDifficultyName by rememberSaveable {
        mutableStateOf(Difficulty.EASY.name)
    }

    var selectedQuestionCount by rememberSaveable {
        mutableIntStateOf(3)
    }

    val selectedTopic =
        QuizTopic.valueOf(selectedTopicName)

    val selectedDifficulty =
        Difficulty.valueOf(selectedDifficultyName)

    val availableDifficulties = listOf(
        Difficulty.EASY,
        Difficulty.MEDIUM
    )

    val availableQuestionCounts = listOf(3, 5)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Choose Your Quiz",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Topic",
            style = MaterialTheme.typography.titleMedium
        )

        QuizTopic.entries.forEach { topic ->
            FilterChip(
                selected = selectedTopicName == topic.name,
                onClick = {
                    selectedTopicName = topic.name
                },
                label = {
                    Text(text = topic.displayName)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = "Difficulty",
            style = MaterialTheme.typography.titleMedium
        )

        availableDifficulties.forEach { difficulty ->
            FilterChip(
                selected =
                    selectedDifficultyName == difficulty.name,
                onClick = {
                    selectedDifficultyName = difficulty.name
                },
                label = {
                    Text(text = difficulty.displayName)
                }
            )
        }

        Text(
            text = "Number of questions",
            style = MaterialTheme.typography.titleMedium
        )

        availableQuestionCounts.forEach { count ->
            FilterChip(
                selected = selectedQuestionCount == count,
                onClick = {
                    selectedQuestionCount = count
                },
                label = {
                    Text(text = count.toString())
                }
            )
        }

        Button(
            onClick = {
                onStartQuiz(
                    QuizConfig(
                        topic = selectedTopic,
                        difficulty = selectedDifficulty,
                        questionCount = selectedQuestionCount
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Begin Quiz")
        }
    }
}