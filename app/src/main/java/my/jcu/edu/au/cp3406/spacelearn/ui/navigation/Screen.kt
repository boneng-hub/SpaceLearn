package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object DailyDetail : Screen("daily_detail")

    data object QuizSetup : Screen("quiz_setup")

    data object Quiz :
        Screen("quiz/{topic}/{difficulty}/{count}") {

        const val TOPIC_ARGUMENT = "topic"
        const val DIFFICULTY_ARGUMENT = "difficulty"
        const val COUNT_ARGUMENT = "count"

        fun createRoute(config: QuizConfig): String {
            return "quiz/" +
                    "${config.topic.name}/" +
                    "${config.difficulty.name}/" +
                    config.questionCount
        }
    }

    data object QuizResult :
        Screen("quiz_result/{score}/{total}") {

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