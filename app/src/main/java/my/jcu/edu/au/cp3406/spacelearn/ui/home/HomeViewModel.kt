package my.jcu.edu.au.cp3406.spacelearn.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

class HomeViewModel(
    private val astronomyRepository:
    AstronomyRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        loadDailyContent()
    }

    fun loadDailyContent() {
        if (_uiState.value.isLoading) {
            return
        }

        _uiState.update { state ->
            state.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            runCatching {
                astronomyRepository
                    .getDailyContent()
            }.onSuccess { content ->
                _uiState.value = HomeUiState(
                    dailyContent = content,
                    isLoading = false
                )
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage =
                            "Unable to load today's space discovery. Check your connection and try again."
                    )
                }
            }
        }
    }
}