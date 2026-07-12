package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun QuizResultScreen(
    score: Int,
    totalQuestions: Int,
    onTryAgain: () -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accuracy = if (totalQuestions == 0) {
        0
    } else {
        score * 100 / totalQuestions
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quiz Complete",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$score / $totalQuestions",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Accuracy: $accuracy%",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = resultMessage(accuracy),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onTryAgain,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Try Another Quiz")
        }

        OutlinedButton(
            onClick = onBackHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back to Home")
        }
    }
}

private fun resultMessage(
    accuracy: Int
): String {
    return when {
        accuracy >= 80 ->
            "Great work. You have a strong understanding of this topic."

        accuracy >= 60 ->
            "Good effort. Review the explanations and try again."

        else ->
            "Keep learning. Reviewing the topic will help strengthen your knowledge."
    }
}