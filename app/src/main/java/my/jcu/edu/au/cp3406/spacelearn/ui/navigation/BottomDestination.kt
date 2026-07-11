package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

data class BottomDestination(
    val route: String,
    val label: String,
    val symbol: String
)

val bottomDestinations = listOf(
    BottomDestination(
        route = Screen.Home.route,
        label = "Home",
        symbol = "H"
    ),
    BottomDestination(
        route = Screen.QuizSetup.route,
        label = "Quiz",
        symbol = "Q"
    ),
    BottomDestination(
        route = Screen.Statistics.route,
        label = "Statistics",
        symbol = "S"
    ),
    BottomDestination(
        route = Screen.Settings.route,
        label = "Settings",
        symbol = "⚙"
    )
)