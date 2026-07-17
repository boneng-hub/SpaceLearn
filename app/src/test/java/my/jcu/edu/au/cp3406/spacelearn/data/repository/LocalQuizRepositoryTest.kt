package my.jcu.edu.au.cp3406.spacelearn.data.repository

import my.jcu.edu.au.cp3406.spacelearn.data.local.LocalQuestionBank
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

    @Test
    fun getQuestions_withRandomiseDisabled_keepsOriginalOrder() {
        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3,
            randomiseQuestions = false
        )

        val questions =
            repository.getQuestions(config)

        val expectedIds =
            LocalQuestionBank.questions
                .filter { question ->
                    question.topic ==
                            QuizTopic.PLANETS &&
                            question.difficulty ==
                            Difficulty.EASY
                }
                .take(3)
                .map { question ->
                    question.id
                }

        val actualIds =
            questions.map { question ->
                question.id
            }

        assertEquals(
            expectedIds,
            actualIds
        )
    }

    @Test
    fun getQuestions_withRandomiseEnabled_returnsValidQuestions() {
        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3,
            randomiseQuestions = true
        )

        val questions =
            repository.getQuestions(config)

        assertTrue(
            questions.size <=
                    config.questionCount
        )

        assertTrue(
            questions.all { question ->
                question.topic ==
                        config.topic &&
                        question.difficulty ==
                        config.difficulty
            }
        )
    }
}