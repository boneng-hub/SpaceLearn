package my.jcu.edu.au.cp3406.spacelearn.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

@Composable
fun HomeRoute(
    astronomyRepository: AstronomyRepository,
    onStartQuiz: () -> Unit,
    onOpenDailyContent: () -> Unit,
    modifier: Modifier = Modifier
) {
    val factory = remember(astronomyRepository) {
        HomeViewModelFactory(
            astronomyRepository =
                astronomyRepository
        )
    }

    val viewModel: HomeViewModel =
        viewModel(factory = factory)

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRetry = viewModel::loadDailyContent,
        onStartQuiz = onStartQuiz,
        onOpenDailyContent =
            onOpenDailyContent,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onStartQuiz: () -> Unit,
    onOpenDailyContent: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "SpaceLearn",
                style =
                    MaterialTheme.typography
                        .headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 20.dp
                )
            )

            Text(
                text = "Explore the universe",
                style =
                    MaterialTheme.typography
                        .bodyLarge
            )
        }

        item {
            Text(
                text = "Daily Space Discovery",
                style =
                    MaterialTheme.typography
                        .titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            when {
                uiState.isLoading -> {
                    LoadingDailyContent()
                }

                uiState.errorMessage != null -> {
                    DailyContentError(
                        message =
                            uiState.errorMessage,
                        onRetry = onRetry
                    )
                }

                uiState.dailyContent != null -> {
                    DailyContentCard(
                        title =
                            uiState.dailyContent.title,
                        date =
                            uiState.dailyContent.date,
                        explanation =
                            uiState.dailyContent
                                .explanation,
                        imageUrl =
                            uiState.dailyContent
                                .imageUrl,
                        source =
                            uiState.dailyContent
                                .sourceLabel,
                        onOpen =
                            onOpenDailyContent
                    )
                }
            }
        }

        item {
            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Start a Quiz")
            }
        }

        item {
            Text(
                text = "Your quiz results are saved locally on this device.",
                style =
                    MaterialTheme.typography
                        .bodySmall,
                modifier = Modifier.padding(
                    bottom = 24.dp
                )
            )
        }
    }
}

@Composable
private fun LoadingDailyContent(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator()

            Text(
                text =
                    "Loading today's discovery..."
            )
        }
    }
}

@Composable
private fun DailyContentError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Content unavailable",
                fontWeight = FontWeight.Bold
            )

            Text(text = message)

            OutlinedButton(
                onClick = onRetry
            ) {
                Text(text = "Try Again")
            }
        }
    }
}

@Composable
private fun DailyContentCard(
    title: String,
    date: String,
    explanation: String,
    imageUrl: String?,
    source: String,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription =
                        "Astronomy image: $title",
                    contentScale =
                        ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            } else {
                Text(
                    text =
                        "A preview image is not available for today's media.",
                    modifier =
                        Modifier.padding(18.dp)
                )
            }

            Column(
                modifier =
                    Modifier.padding(18.dp),
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style =
                        MaterialTheme.typography
                            .titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = date,
                    style =
                        MaterialTheme.typography
                            .bodySmall
                )

                Text(
                    text =
                        explanation.toPreviewText(),
                    style =
                        MaterialTheme.typography
                            .bodyMedium
                )

                Text(
                    text = source,
                    style =
                        MaterialTheme.typography
                            .labelSmall
                )

                Button(
                    onClick = onOpen,
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(text = "Explore")
                }
            }
        }
    }
}

private fun String.toPreviewText(): String {
    val maximumLength = 180

    return if (length <= maximumLength) {
        this
    } else {
        take(maximumLength)
            .trimEnd() + "..."
    }
}