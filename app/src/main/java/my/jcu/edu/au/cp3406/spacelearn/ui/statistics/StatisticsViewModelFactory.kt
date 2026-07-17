package my.jcu.edu.au.cp3406.spacelearn.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository

class StatisticsViewModelFactory(
    private val progressRepository: ProgressRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                StatisticsViewModel::class.java
            )
        ) {
            return StatisticsViewModel(
                progressRepository = progressRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}