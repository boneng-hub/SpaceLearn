package my.jcu.edu.au.cp3406.spacelearn

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import my.jcu.edu.au.cp3406.spacelearn.ui.navigation.SpaceLearnNavHost
import my.jcu.edu.au.cp3406.spacelearn.ui.navigation.bottomDestinations

@Composable
fun SpaceLearnApp(
    appContainer: AppContainer
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomRoutes = bottomDestinations
        .map { destination -> destination.route }
        .toSet()

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                NavigationBar {
                    bottomDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(
                                        navController.graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Text(text = destination.symbol)
                            },
                            label = {
                                Text(text = destination.label)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        SpaceLearnNavHost(
            navController = navController,
            quizRepository = appContainer.quizRepository,
            progressRepository = appContainer.progressRepository,
            settingsRepository = appContainer.settingsRepository,
            modifier = Modifier.padding(innerPadding)
        )
    }
}