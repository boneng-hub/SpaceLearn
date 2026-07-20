package my.jcu.edu.au.cp3406.spacelearn.testdouble

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

object QuizTestFixtures {

    val questions = listOf(
        QuizQuestion(
            id = 1,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText =
                "Which planet is known as the Red Planet?",
            options = listOf(
                "Mars",
                "Venus",
                "Jupiter",
                "Mercury"
            ),
            correctAnswerIndex = 0,
            explanation =
                "Mars appears red because of iron oxide."
        ),
        QuizQuestion(
            id = 2,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText =
                "Which planet is the largest?",
            options = listOf(
                "Earth",
                "Jupiter",
                "Mars",
                "Venus"
            ),
            correctAnswerIndex = 1,
            explanation =
                "Jupiter is the largest planet."
        ),
        QuizQuestion(
            id = 3,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText =
                "Which planet is closest to the Sun?",
            options = listOf(
                "Venus",
                "Earth",
                "Mercury",
                "Mars"
            ),
            correctAnswerIndex = 2,
            explanation =
                "Mercury is closest to the Sun."
        )
    )
}