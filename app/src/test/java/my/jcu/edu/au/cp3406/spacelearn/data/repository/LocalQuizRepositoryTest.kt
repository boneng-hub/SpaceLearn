package my.jcu.edu.au.cp3406.spacelearn.data.repository

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LocalQuizRepositoryTest {

    private lateinit var repository: LocalQuizRepository

    @Before
    fun setUp() {
        repository = LocalQuizRepository()
    }

    @Test
    fun getQuestions_returnsSelectedTopicOnly() {
        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3
        )

        val questions =
            repository.getQuestions(config)

        assertTrue(
            questions.all { question ->
                question.topic == QuizTopic.PLANETS
            }
        )
    }

    @Test
    fun getQuestions_returnsSelectedDifficultyOnly() {
        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3
        )

        val questions =
            repository.getQuestions(config)

        assertTrue(
            questions.all { question ->
                question.difficulty == Difficulty.EASY
            }
        )
    }

    @Test
    fun getQuestions_doesNotExceedRequestedCount() {
        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3
        )

        val questions =
            repository.getQuestions(config)

        assertTrue(questions.size <= 3)
    }
}