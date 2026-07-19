package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

import my.jcu.edu.au.cp3406.spacelearn.ui.statistics.StatisticsRoute
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.ui.daily.DailyDetailRoute
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizResultScreen
import androidx.navigation.NavType
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizRoute
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository
import my.jcu.edu.au.cp3406.spacelearn.ui.settings.SettingsRoute
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizSetupRoute
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository
import my.jcu.edu.au.cp3406.spacelearn.ui.home.HomeRoute
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
            HomeRoute(
                onStartQuiz = {
                    navController.navigate(
                        Screen.QuizSetup.route
                    )
                },
                onOpenDailyContent = {
                    navController.navigate(
                        Screen.DailyDetail.route
                    )
                }
            )
        }

        composable(Screen.DailyDetail.route) {
            DailyDetailRoute(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.QuizSetup.route) {
            QuizSetupRoute(
                onStartQuiz = { config ->
                    navController.navigate(
                        Screen.Quiz.createRoute(config)
                    )
                }
            )
        }
        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument(Screen.Quiz.TOPIC_ARGUMENT) {
                    type = NavType.StringType
                },
                navArgument(Screen.Quiz.DIFFICULTY_ARGUMENT) {
                    type = NavType.StringType
                },
                navArgument(Screen.Quiz.COUNT_ARGUMENT) {
                    type = NavType.IntType
                },
                navArgument(Screen.Quiz.RANDOMISE_ARGUMENT) {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->

            val topicName =
                backStackEntry.arguments?.getString(
                    Screen.Quiz.TOPIC_ARGUMENT
                )

            val difficultyName =
                backStackEntry.arguments?.getString(
                    Screen.Quiz.DIFFICULTY_ARGUMENT
                )

            val requestedCount =
                backStackEntry.arguments?.getInt(
                    Screen.Quiz.COUNT_ARGUMENT
                ) ?: 3

            val topic =
                QuizTopic.entries.firstOrNull {
                    it.name == topicName
                } ?: QuizTopic.SOLAR_SYSTEM

            val difficulty =
                Difficulty.entries.firstOrNull {
                    it.name == difficultyName
                } ?: Difficulty.EASY

            val questionCount =
                if (requestedCount == 5) 5 else 3

            val randomiseQuestions =
                backStackEntry.arguments?.getBoolean(
                    Screen.Quiz.RANDOMISE_ARGUMENT
                ) ?: false

            val config = QuizConfig(
                topic = topic,
                difficulty = difficulty,
                questionCount = questionCount,
                randomiseQuestions = randomiseQuestions
            )

            QuizRoute(
                config = config,
                onQuizComplete = { score, total ->
                    navController.navigate(
                        Screen.QuizResult.createRoute(
                            score = score,
                            total = total
                        )
                    ) {
                        popUpTo(Screen.Quiz.route) {
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
            StatisticsRoute()
        }

        composable(Screen.Settings.route) {
            SettingsRoute()
        }
    }
}