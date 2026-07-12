package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.ui.daily.DailyDetailScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.home.HomeScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizResultScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizSetupScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.settings.SettingsScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.statistics.StatisticsScreen
import androidx.navigation.NavType
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizRoute
@Composable
fun SpaceLearnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartQuiz = {
                    navController.navigate(Screen.QuizSetup.route)
                },
                onOpenDailyContent = {
                    navController.navigate(Screen.DailyDetail.route)
                }
            )
        }

        composable(Screen.DailyDetail.route) {
            DailyDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.QuizSetup.route) {
            QuizSetupScreen(
                onStartQuiz = { selectedTopic ->
                    navController.navigate(
                        Screen.Quiz.createRoute(selectedTopic)
                    )
                }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument(Screen.Quiz.TOPIC_ARGUMENT) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val topicName =
                backStackEntry.arguments?.getString(
                    Screen.Quiz.TOPIC_ARGUMENT
                )

            val selectedTopic =
                 QuizTopic.entries.firstOrNull { topic ->
                     topic.name == topicName
                 } ?: QuizTopic.SOLAR_SYSTEM

            QuizRoute(
                 topic = selectedTopic,
                 onQuizComplete = { score, total ->
                     navController.navigate(
                         Screen.QuizResult.createRoute(
                             score = score,
                             total = total
                         )
                     ) {
                         popUpTo(Screen.QuizSetup.route) {
                             inclusive = true
                         }
                     }
                 }
            )
        }
        composable(
            route = Screen.QuizResult.route,
            arguments = listOf(
                navArgument(
                    Screen.QuizResult.SCORE_ARGUMENT
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    Screen.QuizResult.TOTAL_ARGUMENT
                ) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val score =
                backStackEntry.arguments?.getInt(
                    Screen.QuizResult.SCORE_ARGUMENT
                ) ?: 0

            val total =
                backStackEntry.arguments?.getInt(
                    Screen.QuizResult.TOTAL_ARGUMENT
                ) ?: 0

            QuizResultScreen(
                score = score,
                totalQuestions = total,
                onTryAgain = {
                    navController.navigate(
                        Screen.QuizSetup.route
                    ) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                },
                onBackHome = {
                    navController.navigate(
                        Screen.Home.route
                    ) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Statistics.route) {
            StatisticsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}