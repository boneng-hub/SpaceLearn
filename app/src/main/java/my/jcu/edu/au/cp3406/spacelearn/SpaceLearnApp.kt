package my.jcu.edu.au.cp3406.spacelearn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import my.jcu.edu.au.cp3406.spacelearn.ui.navigation.SpaceLearnNavHost

/**
 * Root composable for the SpaceLearn app.
 * Hosts the main Scaffold with a placeholder bottom navigation bar and the NavHost.
 * TODO: Replace placeholder nav items with real Screen destinations and icons.
 */
@Composable
fun SpaceLearnApp() {
    // Placeholder nav items — replace with Screen routes and icons later
    val navItems = listOf("Home", "Quiz", "Stats", "Settings")
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        label = { Text(text = label) },
                        icon = { Text(text = label.first().toString()) } // placeholder icon
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TODO: Replace with real navigation once navigation-compose is added
            SpaceLearnNavHost()
        }
    }
}

