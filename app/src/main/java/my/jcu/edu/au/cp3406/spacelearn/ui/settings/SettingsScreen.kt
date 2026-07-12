package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Settings",
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    )
}