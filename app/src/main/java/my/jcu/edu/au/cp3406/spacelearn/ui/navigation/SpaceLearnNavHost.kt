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
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
@Composable
fun SpaceLearnNavHost(
    navController: NavHostController,
    quizRepository: QuizRepository,
    progressRepository: ProgressRepository,
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

            val config = QuizConfig(
                topic = topic,
                difficulty = difficulty,
                questionCount = questionCount
            )

            QuizRoute(
                config = config,
                quizRepository = quizRepository,
                progressRepository  = progressRepository,
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