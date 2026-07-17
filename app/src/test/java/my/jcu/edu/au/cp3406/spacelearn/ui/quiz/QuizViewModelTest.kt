package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import my.jcu.edu.au.cp3406.spacelearn.data.repository.LocalQuizRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel

    private val testConfig = QuizConfig(
        topic = QuizTopic.PLANETS,
        difficulty = Difficulty.EASY,
        questionCount = 3
    )

    @Before
    fun setUp() {
        viewModel = QuizViewModel(
            quizRepository = LocalQuizRepository(),
            progressRepository = FakeProgressRepository()
        )

        viewModel.startQuiz(testConfig)
    }

    @Test
    fun startQuiz_loadsThreeQuestions() {
        val state = viewModel.uiState.value

        assertEquals(3, state.questions.size)
        assertEquals(0, state.currentQuestionIndex)
        assertEquals(0, state.score)
        assertFalse(state.isQuizComplete)
    }

    @Test
    fun startQuiz_loadsOnlySelectedTopic() {
        val state = viewModel.uiState.value

        assertTrue(
            state.questions.all { question ->
                question.topic == QuizTopic.PLANETS
            }
        )
    }

    @Test
    fun selectCorrectAnswer_increasesScore() {
        val initialState = viewModel.uiState.value
        val correctIndex =
            initialState.currentQuestion!!.correctAnswerIndex

        viewModel.selectAnswer(correctIndex)

        val updatedState = viewModel.uiState.value

        assertEquals(1, updatedState.score)
        assertTrue(
            updatedState.isCurrentAnswerCorrect == true
        )
    }

    @Test
    fun selectingAnswerTwice_doesNotIncreaseScoreTwice() {
        val correctIndex =
            viewModel.uiState.value
                .currentQuestion!!
                .correctAnswerIndex

        viewModel.selectAnswer(correctIndex)
        viewModel.selectAnswer(correctIndex)

        assertEquals(
            1,
            viewModel.uiState.value.score
        )
    }

    @Test
    fun moveToNextQuestion_clearsSelectedAnswer() {
        val correctIndex =
            viewModel.uiState.value
                .currentQuestion!!
                .correctAnswerIndex

        viewModel.selectAnswer(correctIndex)
        viewModel.moveToNextQuestion()

        val updatedState = viewModel.uiState.value

        assertEquals(
            1,
            updatedState.currentQuestionIndex
        )
        assertNull(updatedState.selectedAnswerIndex)
    }

    @Test
    fun startQuiz_filtersQuestionsByDifficulty() {
        val state = viewModel.uiState.value

        assertTrue(
            state.questions.all { question ->
                question.difficulty ==
                        Difficulty.EASY
            }
        )
    }

    @Test
    fun startQuiz_respectsRequestedQuestionCount() {
        val state = viewModel.uiState.value

        assertEquals(
            testConfig.questionCount,
            state.questions.size
        )
    }
}

private class FakeProgressRepository : ProgressRepository {
    override suspend fun saveQuizResult(result: QuizResult) {
        // No-op for unit tests that do not assert persistence.
    }

    override fun observeQuizResults(): Flow<List<QuizResult>> {
        return flowOf(emptyList())
    }

    override suspend fun clearQuizResults() {
        // No-op for tests.
    }
}
