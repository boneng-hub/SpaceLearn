package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import my.jcu.edu.au.cp3406.spacelearn.domain.model.AppSettings

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val isLoading: Boolean = true,
    val isClearingHistory: Boolean = false
)