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
        observeCachedContent()
        refreshDailyContent()
    }

    private fun observeCachedContent() {
        viewModelScope.launch {
            astronomyRepository
                .observeLatestContent()
                .collect { content ->

                    if (content != null) {
                        _uiState.update { state ->
                            state.copy(
                                dailyContent = content,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    fun refreshDailyContent() {
        if (_uiState.value.isRefreshing) {
            return
        }

        _uiState.update { state ->
            state.copy(
                isLoading =
                    state.dailyContent == null,
                isRefreshing = true,
                isUsingCachedContent = false,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                astronomyRepository
                    .refreshDailyContent()
            } catch (_: Exception) {
                _uiState.update { state ->
                    val hasCachedContent =
                        state.dailyContent != null

                    state.copy(
                        isUsingCachedContent =
                            hasCachedContent,
                        errorMessage =
                            if (hasCachedContent) {
                                "Unable to refresh. Showing the most recently saved discovery."
                            } else {
                                "Unable to load today's discovery. Check your connection and try again."
                            }
                    )
                }
            } finally {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }
}