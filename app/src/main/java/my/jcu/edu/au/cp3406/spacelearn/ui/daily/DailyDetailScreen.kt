package my.jcu.edu.au.cp3406.spacelearn.ui.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

@Composable
fun DailyDetailRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: DailyDetailViewModel =
        hiltViewModel()

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    DailyDetailScreen(
        uiState = uiState,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
fun DailyDetailScreen(
    uiState: DailyDetailUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val content = uiState.content

    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement =
                Arrangement.Center,
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        return
    }

    if (content == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement =
                Arrangement.Center
        ) {
            Text(
                text =
                    "No daily discovery is available."
            )

            Button(
                onClick = onBack,
                modifier =
                    Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Back")
            }
        }

        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        item {
            Button(
                onClick = onBack,
                modifier =
                    Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Back")
            }
        }

        item {
            content.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription =
                        "Astronomy image: ${content.title}",
                    contentScale =
                        ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
            }
        }

        item {
            Text(
                text = content.title,
                style =
                    MaterialTheme.typography
                        .headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = content.date,
                style =
                    MaterialTheme.typography
                        .bodySmall
            )
        }

        item {
            Text(
                text = content.explanation,
                style =
                    MaterialTheme.typography
                        .bodyLarge
            )
        }

        item {
            Text(
                text = content.sourceLabel,
                style =
                    MaterialTheme.typography
                        .labelMedium,
                modifier =
                    Modifier.padding(bottom = 24.dp)
            )
        }
    }
}