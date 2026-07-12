package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizRoute(
    onQuizComplete: (
        score: Int,
        totalQuestions: Int
    ) -> Unit,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isQuizComplete) {
        if (uiState.isQuizComplete) {
            onQuizComplete(
                uiState.score,
                uiState.questions.size
            )
        }
    }

    QuizScreen(
        uiState = uiState,
        onAnswerSelected = viewModel::selectAnswer,
        onNextQuestion = viewModel::moveToNextQuestion
    )
}

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerSelected: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = uiState.currentQuestion

    if (question == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No quiz questions are available.")
        }

        return
    }

    val isLastQuestion =
        uiState.currentQuestionIndex == uiState.questions.lastIndex

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = question.topic.displayName,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Question ${uiState.currentQuestionIndex + 1} " +
                    "of ${uiState.questions.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        LinearProgressIndicator(
            progress = { uiState.progress },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = question.questionText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        question.options.forEachIndexed { index, option ->

            val answerNote = when {
                !uiState.hasAnswered -> ""

                index == question.correctAnswerIndex ->
                    " — Correct answer"

                index == uiState.selectedAnswerIndex ->
                    " — Your answer"

                else -> ""
            }

            OutlinedButton(
                onClick = {
                    onAnswerSelected(index)
                },
                enabled = !uiState.hasAnswered,
                modifier = Modifier.fillMaxWidth()
            ) {
                val optionLetter =
                    ('A'.code + index).toChar()

                Text(
                    text = "$optionLetter. $option$answerNote"
                )
            }
        }

        if (uiState.hasAnswered) {
            AnswerFeedbackCard(
                isCorrect =
                    uiState.isCurrentAnswerCorrect == true,
                explanation = question.explanation
            )

            Button(
                onClick = onNextQuestion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isLastQuestion) {
                        "Finish Quiz"
                    } else {
                        "Next Question"
                    }
                )
            }
        }
    }
}

@Composable
private fun AnswerFeedbackCard(
    isCorrect: Boolean,
    explanation: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isCorrect) {
                    "Correct"
                } else {
                    "Not quite"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(text = explanation)
        }
    }
}