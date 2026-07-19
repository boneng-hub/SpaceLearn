package my.jcu.edu.au.cp3406.spacelearn.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DailyDetailViewModel @Inject constructor(
    astronomyRepository: AstronomyRepository
) : ViewModel() {
    val uiState: StateFlow<DailyDetailUiState> =
        astronomyRepository
            .observeLatestContent()
            .map { content ->
                DailyDetailUiState(
                    content = content,
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        stopTimeoutMillis = 5_000
                    ),
                initialValue =
                    DailyDetailUiState()
            )
}