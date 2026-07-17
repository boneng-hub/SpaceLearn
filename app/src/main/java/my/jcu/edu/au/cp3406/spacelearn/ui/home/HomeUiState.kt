package my.jcu.edu.au.cp3406.spacelearn.ui.home

import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent

data class HomeUiState(
    val dailyContent: AstronomyContent? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)