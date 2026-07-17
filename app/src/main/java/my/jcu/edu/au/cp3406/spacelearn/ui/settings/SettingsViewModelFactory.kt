package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                SettingsViewModel::class.java
            )
        ) {
            return SettingsViewModel(
                settingsRepository =
                    settingsRepository,
                progressRepository =
                    progressRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}