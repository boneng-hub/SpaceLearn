package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel

    @Before
    fun setUp() {
        viewModel = QuizViewModel()

        viewModel.startQuiz(
            topic = QuizTopic.PLANETS
        )
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
}