package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository
import androidx.compose.ui.platform.testTag

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier
) {
    val viewModel: SettingsViewModel =
        hiltViewModel()

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onDifficultyChanged =
            viewModel::updateDefaultDifficulty,
        onQuestionCountChanged =
            viewModel::updateDefaultQuestionCount,
        onRandomiseChanged =
            viewModel::updateRandomiseQuestions,
        onClearHistory =
            viewModel::clearQuizHistory,
        modifier = modifier
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onDifficultyChanged: (Difficulty) -> Unit,
    onQuestionCountChanged: (Int) -> Unit,
    onRandomiseChanged: (Boolean) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showClearHistoryDialog by remember {
        mutableStateOf(false)
    }

    if (showClearHistoryDialog) {
        AlertDialog(
            onDismissRequest = {
                showClearHistoryDialog = false
            },
            modifier = Modifier.testTag(
                SettingsScreenTestTags.CLEAR_DIALOG),
            title = {
                Text(text = "Clear quiz history?")
            },
            text = {
                Text(
                    text = "All saved quiz results and statistics will be deleted. This action cannot be undone."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showClearHistoryDialog = false
                        onClearHistory()
                    },
                    modifier = Modifier.testTag(
                        SettingsScreenTestTags
                            .CLEAR_DIALOG_CONFIRM
                    )
                ) {
                    Text(text = "Clear")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearHistoryDialog = false
                    },
                    modifier = Modifier.testTag(
                        SettingsScreenTestTags
                            .CLEAR_DIALOG_CANCEL
                    )
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement =
                Arrangement.Center,
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag(SettingsScreenTestTags.SCREEN_LIST)
            .padding(horizontal = 20.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style =
                    MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )
        }

        item {
            Text(
                text = "Quiz Preferences",
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = "Default difficulty",
                style =
                    MaterialTheme.typography.titleMedium
            )
        }

        item {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    Difficulty.EASY,
                    Difficulty.MEDIUM
                ).forEach { difficulty ->
                    FilterChip(
                        selected =
                            uiState.settings
                                .defaultDifficulty ==
                                    difficulty,
                        onClick = {
                            onDifficultyChanged(
                                difficulty
                            )
                        },
                        label = {
                            Text(
                                text =
                                    difficulty.displayName
                            )
                        },
                        modifier = Modifier.testTag(
                            SettingsScreenTestTags
                                .difficulty(difficulty)
                        )
                    )
                }
            }
        }

        item {
            Text(
                text = "Default number of questions",
                style =
                    MaterialTheme.typography.titleMedium
            )
        }

        item {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                listOf(3, 5).forEach { count ->
                    FilterChip(
                        selected =
                            uiState.settings
                                .defaultQuestionCount ==
                                    count,
                        onClick = {
                            onQuestionCountChanged(count)
                        },
                        label = {
                            Text(text = count.toString())
                        },
                        modifier = Modifier.testTag(
                            SettingsScreenTestTags
                                .questionCount(count)
                        )
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {
                    Text(
                        text = "Randomise questions",
                        style =
                            MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Show questions in a different order.",
                        style =
                            MaterialTheme.typography.bodySmall
                    )
                }

                Switch(
                    checked =
                        uiState.settings
                            .randomiseQuestions,
                    onCheckedChange =
                        onRandomiseChanged,
                    modifier = Modifier.testTag(
                        SettingsScreenTestTags
                            .RANDOMISE_SWITCH
                    )
                )
            }
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Learning Data",
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            OutlinedButton(
                onClick = {
                    showClearHistoryDialog = true
                },
                enabled =
                    !uiState.isClearingHistory,
                modifier = Modifier.fillMaxWidth()
                    .testTag(
                        SettingsScreenTestTags
                            .CLEAR_HISTORY_BUTTON
                    )
            ) {
                Text(
                    text =
                        if (
                            uiState.isClearingHistory
                        ) {
                            "Clearing History..."
                        } else {
                            "Clear Quiz History"
                        }
                )
            }
        }

        item {
            Text(
                text = "Quiz results are stored locally on this device.",
                style =
                    MaterialTheme.typography.bodySmall
            )
        }

        item {
            Button(
                onClick = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text =
                        "More accessibility settings coming later"
                )
            }
        }
    }
}