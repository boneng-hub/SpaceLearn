package my.jcu.edu.au.cp3406.spacelearn.data.local

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

object LocalQuestionBank {

    val questions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            topic = QuizTopic.SOLAR_SYSTEM,
            difficulty = Difficulty.EASY,
            questionText = "What is at the centre of the Solar System?",
            options = listOf(
                "Earth",
                "The Moon",
                "The Sun",
                "Jupiter"
            ),
            correctAnswerIndex = 2,
            explanation = "The planets in the Solar System orbit the Sun."
        ),
        QuizQuestion(
            id = 2,
            topic = QuizTopic.SOLAR_SYSTEM,
            difficulty = Difficulty.EASY,
            questionText = "How many planets are in the Solar System?",
            options = listOf(
                "Seven",
                "Eight",
                "Nine",
                "Ten"
            ),
            correctAnswerIndex = 1,
            explanation = "The Solar System contains eight recognised planets."
        ),
        QuizQuestion(
            id = 3,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText = "Which planet is closest to the Sun?",
            options = listOf(
                "Venus",
                "Earth",
                "Mars",
                "Mercury"
            ),
            correctAnswerIndex = 3,
            explanation = "Mercury is the closest planet to the Sun."
        ),
        QuizQuestion(
            id = 4,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText = "Which is the largest planet in the Solar System?",
            options = listOf(
                "Saturn",
                "Jupiter",
                "Neptune",
                "Earth"
            ),
            correctAnswerIndex = 1,
            explanation = "Jupiter is the largest planet in the Solar System."
        ),
        QuizQuestion(
            id = 5,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText = "Which planet is famous for its visible rings?",
            options = listOf(
                "Mars",
                "Venus",
                "Saturn",
                "Mercury"
            ),
            correctAnswerIndex = 2,
            explanation = "Saturn is well known for its large and visible ring system."
        ),
        QuizQuestion(
            id = 6,
            topic = QuizTopic.STARS_AND_GALAXIES,
            difficulty = Difficulty.EASY,
            questionText = "What is the name of the galaxy that contains the Solar System?",
            options = listOf(
                "Andromeda",
                "The Milky Way",
                "Whirlpool Galaxy",
                "Sombrero Galaxy"
            ),
            correctAnswerIndex = 1,
            explanation = "The Solar System is located within the Milky Way galaxy."
        ),
        QuizQuestion(
            id = 7,
            topic = QuizTopic.STARS_AND_GALAXIES,
            difficulty = Difficulty.MEDIUM,
            questionText = "What is the Sun?",
            options = listOf(
                "A planet",
                "A moon",
                "A star",
                "A galaxy"
            ),
            correctAnswerIndex = 2,
            explanation = "The Sun is a star located at the centre of the Solar System."
        ),
        QuizQuestion(
            id = 8,
            topic = QuizTopic.SPACE_EXPLORATION,
            difficulty = Difficulty.EASY,
            questionText = "What vehicle is designed to travel on the surface of another planet?",
            options = listOf(
                "Rover",
                "Submarine",
                "Balloon",
                "Helicopter"
            ),
            correctAnswerIndex = 0,
            explanation = "A rover is designed to move across and explore the surface of another world."
        )
    )
}