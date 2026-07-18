package my.jcu.edu.au.cp3406.spacelearn.ui.daily

import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent

data class DailyDetailUiState(
    val content: AstronomyContent? = null,
    val isLoading: Boolean = true
)