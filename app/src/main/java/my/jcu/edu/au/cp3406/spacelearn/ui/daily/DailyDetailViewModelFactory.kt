package my.jcu.edu.au.cp3406.spacelearn.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

class DailyDetailViewModelFactory(
    private val astronomyRepository:
    AstronomyRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                DailyDetailViewModel::class.java
            )
        ) {
            return DailyDetailViewModel(
                astronomyRepository =
                    astronomyRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}