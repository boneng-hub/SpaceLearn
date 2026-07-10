package my.jcu.edu.au.cp3406.spacelearn.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onStartQuiz: () -> Unit,
    onOpenDailyContent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "SpaceLearn Home")

        Button(onClick = onOpenDailyContent) {
            Text(text = "Open Daily Discovery")
        }

        Button(onClick = onStartQuiz) {
            Text(text = "Start Quiz")
        }
    }
}