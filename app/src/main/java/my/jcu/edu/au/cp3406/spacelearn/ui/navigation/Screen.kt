package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object DailyDetail : Screen("daily_detail")

    data object QuizSetup : Screen("quiz_setup")

    data object Quiz : Screen("quiz")

    data object QuizResult : Screen("quiz_result/{score}/{total}") {

        const val SCORE_ARGUMENT = "score"
        const val TOTAL_ARGUMENT = "total"

        fun createRoute(
            score: Int,
            total: Int
        ): String {
            return "quiz_result/$score/$total"
        }
    }

    data object Statistics : Screen("statistics")

    data object Settings : Screen("settings")
}