package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

class QuizSetupViewModelFactory(
    private val settingsRepository:
    SettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                QuizSetupViewModel::class.java
            )
        ) {
            return QuizSetupViewModel(
                settingsRepository =
                    settingsRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: " +
                    modelClass.name
        )
    }
}