package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.testdouble.FakeProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.testdouble.FakeQuizRepository
import my.jcu.edu.au.cp3406.spacelearn.testdouble.QuizTestFixtures
import my.jcu.edu.au.cp3406.spacelearn.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    @get:Rule
    val mainDispatcherRule =
        MainDispatcherRule()

    private lateinit var quizRepository:
            FakeQuizRepository

    private lateinit var progressRepository:
            FakeProgressRepository

    private lateinit var viewModel:
            QuizViewModel

    private val config = QuizConfig(
        topic = QuizTopic.PLANETS,
        difficulty = Difficulty.EASY,
        questionCount = 3,
        randomiseQuestions = false
    )

    @Before
    fun setUp() {
        quizRepository =
            FakeQuizRepository(
                questions =
                    QuizTestFixtures.questions
            )

        progressRepository =
            FakeProgressRepository()

        viewModel = QuizViewModel(
            quizRepository =
                quizRepository,
            progressRepository =
                progressRepository
        )
    }

    @Test
    fun startQuiz_loadsConfiguredQuestions() {
        viewModel.startQuiz(config)

        val state = viewModel.uiState.value

        assertEquals(
            config,
            state.config
        )

        assertEquals(
            3,
            state.questions.size
        )

        assertEquals(
            0,
            state.currentQuestionIndex
        )

        assertEquals(
            0,
            state.score
        )

        assertFalse(
            state.isQuizComplete
        )
    }

    @Test
    fun selectAnswer_withCorrectAnswer_increasesScore() {
        viewModel.startQuiz(config)

        viewModel.selectAnswer(
            answerIndex = 0
        )

        val state = viewModel.uiState.value

        assertEquals(
            0,
            state.selectedAnswerIndex
        )

        assertEquals(
            1,
            state.score
        )

        assertTrue(
            state.isCurrentAnswerCorrect == true
        )
    }

    @Test
    fun selectAnswer_twice_doesNotIncreaseScoreTwice() {
        viewModel.startQuiz(config)

        viewModel.selectAnswer(
            answerIndex = 0
        )

        viewModel.selectAnswer(
            answerIndex = 0
        )

        assertEquals(
            1,
            viewModel.uiState.value.score
        )
    }

    @Test
    fun moveToNextQuestion_movesForwardAndClearsSelection() {
        viewModel.startQuiz(config)

        viewModel.selectAnswer(
            answerIndex = 0
        )

        viewModel.moveToNextQuestion()

        val state = viewModel.uiState.value

        assertEquals(
            1,
            state.currentQuestionIndex
        )

        assertEquals(
            null,
            state.selectedAnswerIndex
        )

        assertFalse(
            state.isQuizComplete
        )
    }

    @Test
    fun finishingQuiz_savesResultAndCompletesQuiz() =
        runTest {
            viewModel.startQuiz(config)

            viewModel.selectAnswer(
                answerIndex = 0
            )
            viewModel.moveToNextQuestion()

            viewModel.selectAnswer(
                answerIndex = 1
            )
            viewModel.moveToNextQuestion()

            viewModel.selectAnswer(
                answerIndex = 2
            )
            viewModel.moveToNextQuestion()

            advanceUntilIdle()

            val state =
                viewModel.uiState.value

            assertTrue(
                state.isQuizComplete
            )

            assertFalse(
                state.isSavingResult
            )

            assertEquals(
                3,
                state.score
            )

            assertEquals(
                1,
                progressRepository
                    .savedResults.size
            )

            val savedResult =
                progressRepository
                    .savedResults.first()

            assertEquals(
                QuizTopic.PLANETS,
                savedResult.topic
            )

            assertEquals(
                Difficulty.EASY,
                savedResult.difficulty
            )

            assertEquals(
                3,
                savedResult.totalQuestions
            )

            assertEquals(
                3,
                savedResult.correctAnswers
            )
        }
}