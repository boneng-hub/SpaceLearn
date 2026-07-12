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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

@Composable
fun QuizSetupScreen(
    onStartQuiz: (QuizTopic) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTopicName by rememberSaveable {
        mutableStateOf(QuizTopic.SOLAR_SYSTEM.name)
    }

    val selectedTopic =
        QuizTopic.valueOf(selectedTopicName)

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
            text = "Select a topic",
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
            text = "Difficulty: Easy",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Questions: 3",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                onStartQuiz(selectedTopic)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Begin Quiz")
        }
    }
}