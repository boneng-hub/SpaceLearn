package my.jcu.edu.au.cp3406.spacelearn.domain.model

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

/**
 * Represents a single quiz question.
 * TODO: Populate from a data source (local DB, remote API, etc.).
 */
data class QuizQuestion(
    val id: Int = 0,
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0,
    val topic: QuizTopic = QuizTopic(),
    val difficulty: Difficulty = Difficulty.EASY
)


