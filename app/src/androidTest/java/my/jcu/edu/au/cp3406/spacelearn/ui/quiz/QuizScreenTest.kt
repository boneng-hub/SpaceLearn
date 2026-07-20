package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.ui.theme.SpaceLearnTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import androidx.compose.ui.test.assertIsNotEnabled

class QuizScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    @Test
    fun unansweredQuestion_displaysQuestionAndHidesNext() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(),
                    onAnswerSelected = {},
                    onNextQuestion = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.PROGRESS
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Question 1 of 3"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.QUESTION_TEXT
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Which planet is known as the Red Planet?"
            )
            .assertIsDisplayed()

        questions.first()
            .options
            .forEachIndexed { index, option ->
                composeTestRule
                    .onNodeWithTag(
                        QuizScreenTestTags.answer(
                            index
                        )
                    )
                    .assertIsDisplayed()

                val optionLetter =
                    ('A'.code + index).toChar()

                composeTestRule
                    .onNodeWithText(
                        "$optionLetter. $option"
                    )
                    .assertIsDisplayed()
            }

        composeTestRule
            .onAllNodesWithTag(
                QuizScreenTestTags.EXPLANATION
            )
            .assertCountEquals(0)

        composeTestRule
            .onAllNodesWithTag(
                QuizScreenTestTags.NEXT_BUTTON
            )
            .assertCountEquals(0)
    }

    @Test
    fun clickingAnswer_returnsSelectedIndex() {
        var selectedIndex: Int? = null

        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(),
                    onAnswerSelected = { index ->
                        selectedIndex = index
                    },
                    onNextQuestion = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.answer(2)
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                2,
                selectedIndex
            )
        }
    }

    @Test
    fun answeredQuestion_displaysFeedbackAndExplanation() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(
                        selectedAnswerIndex = 0,
                        score = 1
                    ),
                    onAnswerSelected = {},
                    onNextQuestion = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.FEEDBACK
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Correct"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.EXPLANATION
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                questions.first().explanation
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.NEXT_BUTTON
            )
            .assertIsEnabled()

        composeTestRule
            .onNodeWithText(
                "Next Question"
            )
            .assertIsDisplayed()
    }

    @Test
    fun nextQuestionButton_triggersCallback() {
        var nextClicked = false

        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(
                        selectedAnswerIndex = 0,
                        score = 1
                    ),
                    onAnswerSelected = {},
                    onNextQuestion = {
                        nextClicked = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.NEXT_BUTTON
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertTrue(
                nextClicked
            )
        }
    }

    @Test
    fun finalQuestion_displaysFinishQuizButton() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(
                        currentQuestionIndex = 2,
                        selectedAnswerIndex = 2,
                        score = 3
                    ),
                    onAnswerSelected = {},
                    onNextQuestion = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                "Question 3 of 3"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.NEXT_BUTTON
            )
            .assertIsEnabled()

        composeTestRule
            .onNodeWithText(
                "Finish Quiz"
            )
            .assertIsDisplayed()
    }

    @Test
    fun savingResult_disablesButtonAndUpdatesText() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizScreen(
                    uiState = createUiState(
                        currentQuestionIndex = 2,
                        selectedAnswerIndex = 2,
                        score = 3,
                        isSavingResult = true
                    ),
                    onAnswerSelected = {},
                    onNextQuestion = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizScreenTestTags.NEXT_BUTTON
            )
            .assertIsNotEnabled()

        composeTestRule
            .onNodeWithText(
                "Saving Result..."
            )
            .assertIsDisplayed()
    }

    private fun createUiState(
        currentQuestionIndex: Int = 0,
        selectedAnswerIndex: Int? = null,
        score: Int = 0,
        isSavingResult: Boolean = false
    ): QuizUiState {
        return QuizUiState(
            config = config,
            questions = questions,
            currentQuestionIndex =
                currentQuestionIndex,
            selectedAnswerIndex =
                selectedAnswerIndex,
            score = score,
            isQuizComplete = false,
            isSavingResult =
                isSavingResult
        )
    }

    private companion object {

        val config = QuizConfig(
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionCount = 3,
            randomiseQuestions = false
        )

        val questions = listOf(
            QuizQuestion(
                id = 1,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.EASY,
                questionText =
                    "Which planet is known as the Red Planet?",
                options = listOf(
                    "Mars",
                    "Venus",
                    "Jupiter",
                    "Mercury"
                ),
                correctAnswerIndex = 0,
                explanation =
                    "Mars appears red because of iron oxide."
            ),
            QuizQuestion(
                id = 2,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.EASY,
                questionText =
                    "Which planet is the largest?",
                options = listOf(
                    "Earth",
                    "Jupiter",
                    "Mars",
                    "Venus"
                ),
                correctAnswerIndex = 1,
                explanation =
                    "Jupiter is the largest planet."
            ),
            QuizQuestion(
                id = 3,
                topic = QuizTopic.PLANETS,
                difficulty = Difficulty.EASY,
                questionText =
                    "Which planet is closest to the Sun?",
                options = listOf(
                    "Venus",
                    "Earth",
                    "Mercury",
                    "Mars"
                ),
                correctAnswerIndex = 2,
                explanation =
                    "Mercury is closest to the Sun."
            )
        )
    }
}