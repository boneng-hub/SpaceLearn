package my.jcu.edu.au.cp3406.spacelearn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import my.jcu.edu.au.cp3406.spacelearn.ui.daily.DailyDetailScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.home.HomeScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizResultScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.quiz.QuizSetupScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.settings.SettingsScreen
import my.jcu.edu.au.cp3406.spacelearn.ui.statistics.StatisticsScreen

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
                onStartQuiz = {
                    navController.navigate(Screen.Quiz.route)
                }
            )
        }

        composable(Screen.Quiz.route) {
            QuizScreen(
                onQuizComplete = {
                    navController.navigate(Screen.QuizResult.route) {
                        popUpTo(Screen.QuizSetup.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.QuizResult.route) {
            QuizResultScreen(
                onBackHome = {
                    navController.navigate(Screen.Home.route) {
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