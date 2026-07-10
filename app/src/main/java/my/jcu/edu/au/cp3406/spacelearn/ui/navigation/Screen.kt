package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

/**
 * Defines all navigation route strings for the app.
 * Each object represents a single destination.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DailyDetail : Screen("daily_detail")
    object QuizSetup : Screen("quiz_setup")
    object Quiz : Screen("quiz")
    object QuizResult : Screen("quiz_result")
    object Statistics : Screen("statistics")
    object Settings : Screen("settings")
}

