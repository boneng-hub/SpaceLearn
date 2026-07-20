package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.ui.theme.SpaceLearnTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class QuizSetupScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    @Test
    fun defaultState_displaysSavedPreferences() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizSetupScreen(
                    uiState =
                        QuizSetupUiState(
                            selectedTopic =
                                QuizTopic.SOLAR_SYSTEM,
                            selectedDifficulty =
                                Difficulty.EASY,
                            selectedQuestionCount = 3,
                            randomiseQuestions = false,
                            isLoading = false
                        ),
                    onTopicSelected = {},
                    onDifficultySelected = {},
                    onQuestionCountSelected = {},
                    onRandomiseChanged = {},
                    onStartQuiz = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                "Choose Your Quiz"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.topic(
                    QuizTopic.SOLAR_SYSTEM
                )
            )
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.difficulty(
                    Difficulty.EASY
                )
            )
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.questionCount(
                    3
                )
            )
            .assertIsSelected()

        scrollTo(
            QuizSetupTestTags
                .RANDOMISE_SWITCH
        )

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags
                    .RANDOMISE_SWITCH
            )
            .assertIsOff()

        scrollTo(
            QuizSetupTestTags.BEGIN_BUTTON
        )

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.BEGIN_BUTTON
            )
            .assertIsDisplayed()
    }

    @Test
    fun selectingOptions_updatesDisplayedSelection() {
        composeTestRule.setContent {
            var state by remember {
                mutableStateOf(
                    QuizSetupUiState(
                        selectedTopic =
                            QuizTopic.SOLAR_SYSTEM,
                        selectedDifficulty =
                            Difficulty.EASY,
                        selectedQuestionCount = 3,
                        randomiseQuestions = false,
                        isLoading = false
                    )
                )
            }

            SpaceLearnTheme {
                QuizSetupScreen(
                    uiState = state,
                    onTopicSelected = { topic ->
                        state = state.copy(
                            selectedTopic = topic
                        )
                    },
                    onDifficultySelected = {
                            difficulty ->
                        state = state.copy(
                            selectedDifficulty =
                                difficulty
                        )
                    },
                    onQuestionCountSelected = {
                            count ->
                        state = state.copy(
                            selectedQuestionCount =
                                count
                        )
                    },
                    onRandomiseChanged = {
                            enabled ->
                        state = state.copy(
                            randomiseQuestions =
                                enabled
                        )
                    },
                    onStartQuiz = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.topic(
                    QuizTopic.PLANETS
                )
            )
            .performClick()
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.difficulty(
                    Difficulty.MEDIUM
                )
            )
            .performClick()
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.questionCount(
                    5
                )
            )
            .performClick()
            .assertIsSelected()

        scrollTo(
            QuizSetupTestTags
                .RANDOMISE_SWITCH
        )

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags
                    .RANDOMISE_SWITCH
            )
            .performClick()
            .assertIsOn()
    }

    @Test
    fun beginQuiz_returnsCurrentConfiguration() {
        var startedConfig: QuizConfig? = null

        composeTestRule.setContent {
            var state by remember {
                mutableStateOf(
                    QuizSetupUiState(
                        selectedTopic =
                            QuizTopic.SOLAR_SYSTEM,
                        selectedDifficulty =
                            Difficulty.EASY,
                        selectedQuestionCount = 3,
                        randomiseQuestions = false,
                        isLoading = false
                    )
                )
            }

            SpaceLearnTheme {
                QuizSetupScreen(
                    uiState = state,
                    onTopicSelected = { topic ->
                        state = state.copy(
                            selectedTopic = topic
                        )
                    },
                    onDifficultySelected = {
                            difficulty ->
                        state = state.copy(
                            selectedDifficulty =
                                difficulty
                        )
                    },
                    onQuestionCountSelected = {
                            count ->
                        state = state.copy(
                            selectedQuestionCount =
                                count
                        )
                    },
                    onRandomiseChanged = {
                            enabled ->
                        state = state.copy(
                            randomiseQuestions =
                                enabled
                        )
                    },
                    onStartQuiz = {
                        startedConfig =
                            state.toQuizConfig()
                    }
                )
            }
        }

        assertNull(startedConfig)

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.topic(
                    QuizTopic.PLANETS
                )
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.difficulty(
                    Difficulty.MEDIUM
                )
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.questionCount(
                    5
                )
            )
            .performClick()

        scrollTo(
            QuizSetupTestTags
                .RANDOMISE_SWITCH
        )

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags
                    .RANDOMISE_SWITCH
            )
            .performClick()

        scrollTo(
            QuizSetupTestTags.BEGIN_BUTTON
        )

        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.BEGIN_BUTTON
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                QuizConfig(
                    topic =
                        QuizTopic.PLANETS,
                    difficulty =
                        Difficulty.MEDIUM,
                    questionCount = 5,
                    randomiseQuestions = true
                ),
                startedConfig
            )
        }
    }

    @Test
    fun loadingState_displaysProgressMessage() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                QuizSetupScreen(
                    uiState =
                        QuizSetupUiState(
                            isLoading = true
                        ),
                    onTopicSelected = {},
                    onDifficultySelected = {},
                    onQuestionCountSelected = {},
                    onRandomiseChanged = {},
                    onStartQuiz = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                "Loading quiz preferences..."
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Choose Your Quiz"
            )
            .assertIsNotDisplayed()
    }

    private fun scrollTo(
        targetTag: String
    ) {
        composeTestRule
            .onNodeWithTag(
                QuizSetupTestTags.SCREEN_LIST
            )
            .performScrollToNode(
                hasTestTag(targetTag)
            )
    }
}