package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AppSettings
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.ui.theme.SpaceLearnTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    @Test
    fun savedSettings_areDisplayed() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        settings = AppSettings(
                            defaultDifficulty =
                                Difficulty.MEDIUM,
                            defaultQuestionCount = 5,
                            randomiseQuestions = true
                        ),
                        isLoading = false,
                        isClearingHistory = false
                    ),
                    onDifficultyChanged = {},
                    onQuestionCountChanged = {},
                    onRandomiseChanged = {},
                    onClearHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags.difficulty(
                    Difficulty.MEDIUM
                )
            )
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags.questionCount(
                    5
                )
            )
            .assertIsSelected()

        scrollTo(
            SettingsScreenTestTags
                .RANDOMISE_SWITCH
        )

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .RANDOMISE_SWITCH
            )
            .assertIsOn()

        scrollTo(
            SettingsScreenTestTags
                .CLEAR_HISTORY_BUTTON
        )

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .CLEAR_HISTORY_BUTTON
            )
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun changingPreferences_updatesDisplayedState() {
        composeTestRule.setContent {
            var state by remember {
                mutableStateOf(
                    SettingsUiState(
                        settings = AppSettings(
                            defaultDifficulty =
                                Difficulty.MEDIUM,
                            defaultQuestionCount = 5,
                            randomiseQuestions = true
                        ),
                        isLoading = false
                    )
                )
            }

            SpaceLearnTheme {
                SettingsScreen(
                    uiState = state,
                    onDifficultyChanged = {
                            difficulty ->
                        state = state.copy(
                            settings =
                                state.settings.copy(
                                    defaultDifficulty =
                                        difficulty
                                )
                        )
                    },
                    onQuestionCountChanged = {
                            questionCount ->
                        state = state.copy(
                            settings =
                                state.settings.copy(
                                    defaultQuestionCount =
                                        questionCount
                                )
                        )
                    },
                    onRandomiseChanged = {
                            enabled ->
                        state = state.copy(
                            settings =
                                state.settings.copy(
                                    randomiseQuestions =
                                        enabled
                                )
                        )
                    },
                    onClearHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags.difficulty(
                    Difficulty.EASY
                )
            )
            .performClick()
            .assertIsSelected()

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags.questionCount(
                    3
                )
            )
            .performClick()
            .assertIsSelected()

        scrollTo(
            SettingsScreenTestTags
                .RANDOMISE_SWITCH
        )

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .RANDOMISE_SWITCH
            )
            .performClick()
            .assertIsOff()
    }

    @Test
    fun clearHistoryDialog_cancelDoesNotClearHistory() {
        var clearHistoryCalled = false

        composeTestRule.setContent {
            SpaceLearnTheme {
                SettingsScreen(
                    uiState = loadedState(),
                    onDifficultyChanged = {},
                    onQuestionCountChanged = {},
                    onRandomiseChanged = {},
                    onClearHistory = {
                        clearHistoryCalled = true
                    }
                )
            }
        }

        openClearHistoryDialog()

        composeTestRule
            .onNodeWithText(
                "Clear quiz history?"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .CLEAR_DIALOG_CANCEL
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertFalse(clearHistoryCalled)
        }

        composeTestRule
            .onAllNodesWithTag(
                SettingsScreenTestTags
                    .CLEAR_DIALOG
            )
            .assertCountEquals(0)
    }

    @Test
    fun clearHistoryDialog_confirmCallsClearHistory() {
        var clearHistoryCalled = false

        composeTestRule.setContent {
            SpaceLearnTheme {
                SettingsScreen(
                    uiState = loadedState(),
                    onDifficultyChanged = {},
                    onQuestionCountChanged = {},
                    onRandomiseChanged = {},
                    onClearHistory = {
                        clearHistoryCalled = true
                    }
                )
            }
        }

        openClearHistoryDialog()

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .CLEAR_DIALOG_CONFIRM
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertTrue(clearHistoryCalled)
        }

        composeTestRule
            .onAllNodesWithTag(
                SettingsScreenTestTags
                    .CLEAR_DIALOG
            )
            .assertCountEquals(0)
    }

    @Test
    fun clearingHistory_disablesClearButton() {
        composeTestRule.setContent {
            SpaceLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        settings = AppSettings(),
                        isLoading = false,
                        isClearingHistory = true
                    ),
                    onDifficultyChanged = {},
                    onQuestionCountChanged = {},
                    onRandomiseChanged = {},
                    onClearHistory = {}
                )
            }
        }

        scrollTo(
            SettingsScreenTestTags
                .CLEAR_HISTORY_BUTTON
        )

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .CLEAR_HISTORY_BUTTON
            )
            .assertIsNotEnabled()

        composeTestRule
            .onNodeWithText(
                "Clearing History..."
            )
            .assertIsDisplayed()
    }

    private fun openClearHistoryDialog() {
        scrollTo(
            SettingsScreenTestTags
                .CLEAR_HISTORY_BUTTON
        )

        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .CLEAR_HISTORY_BUTTON
            )
            .performClick()
    }

    private fun scrollTo(
        targetTag: String
    ) {
        composeTestRule
            .onNodeWithTag(
                SettingsScreenTestTags
                    .SCREEN_LIST
            )
            .performScrollToNode(
                hasTestTag(targetTag)
            )
    }

    private fun loadedState():
            SettingsUiState {

        return SettingsUiState(
            settings = AppSettings(
                defaultDifficulty =
                    Difficulty.EASY,
                defaultQuestionCount = 3,
                randomiseQuestions = false
            ),
            isLoading = false,
            isClearingHistory = false
        )
    }
}