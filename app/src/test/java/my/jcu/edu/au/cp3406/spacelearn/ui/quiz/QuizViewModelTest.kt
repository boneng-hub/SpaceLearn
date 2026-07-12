package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

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
    }

    @Test
    fun startQuiz_loadsFiveQuestions() {
        val state = viewModel.uiState.value

        assertEquals(5, state.questions.size)
        assertEquals(0, state.currentQuestionIndex)
        assertEquals(0, state.score)
        assertFalse(state.isQuizComplete)
    }

    @Test
    fun selectCorrectAnswer_increasesScore() {
        val initialState = viewModel.uiState.value
        val correctIndex =
            initialState.currentQuestion!!.correctAnswerIndex

        viewModel.selectAnswer(correctIndex)

        val updatedState = viewModel.uiState.value

        assertEquals(1, updatedState.score)
        assertEquals(correctIndex, updatedState.selectedAnswerIndex)
        assertTrue(updatedState.isCurrentAnswerCorrect == true)
    }

    @Test
    fun selectingAnswerTwice_doesNotIncreaseScoreTwice() {
        val initialState = viewModel.uiState.value
        val correctIndex =
            initialState.currentQuestion!!.correctAnswerIndex

        viewModel.selectAnswer(correctIndex)
        viewModel.selectAnswer(correctIndex)

        val updatedState = viewModel.uiState.value

        assertEquals(1, updatedState.score)
    }

    @Test
    fun moveToNextQuestion_clearsSelectedAnswer() {
        val initialState = viewModel.uiState.value
        val correctIndex =
            initialState.currentQuestion!!.correctAnswerIndex

        viewModel.selectAnswer(correctIndex)
        viewModel.moveToNextQuestion()

        val updatedState = viewModel.uiState.value

        assertEquals(1, updatedState.currentQuestionIndex)
        assertNull(updatedState.selectedAnswerIndex)
    }

    @Test
    fun moveToNextQuestion_withoutAnswer_doesNothing() {
        viewModel.moveToNextQuestion()

        val state = viewModel.uiState.value

        assertEquals(0, state.currentQuestionIndex)
        assertNull(state.selectedAnswerIndex)
    }
}