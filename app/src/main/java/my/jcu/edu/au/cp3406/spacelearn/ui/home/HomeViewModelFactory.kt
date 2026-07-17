package my.jcu.edu.au.cp3406.spacelearn.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

class HomeViewModelFactory(
    private val astronomyRepository:
    AstronomyRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                HomeViewModel::class.java
            )
        ) {
            return HomeViewModel(
                astronomyRepository =
                    astronomyRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: " +
                    modelClass.name
        )
    }
}