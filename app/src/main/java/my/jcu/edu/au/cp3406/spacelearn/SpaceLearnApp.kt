package my.jcu.edu.au.cp3406.spacelearn

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import my.jcu.edu.au.cp3406.spacelearn.ui.navigation.SpaceLearnNavHost

@Composable
fun SpaceLearnApp() {
    val navController = rememberNavController()

    SpaceLearnNavHost(
        navController = navController
    )
}