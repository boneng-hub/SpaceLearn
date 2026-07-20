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
        ),
        QuizQuestion(
            id = 9,
            topic = QuizTopic.SOLAR_SYSTEM,
            difficulty = Difficulty.EASY,
            questionText = "What force keeps planets in orbit around the Sun?",
            options = listOf(
                "Magnetism",
                "Gravity",
                "Friction",
                "Electricity"
            ),
            correctAnswerIndex = 1,
            explanation = "Gravity from the Sun keeps planets moving in orbit."
        ),
        QuizQuestion(
            id = 10,
            topic = QuizTopic.SOLAR_SYSTEM,
            difficulty = Difficulty.MEDIUM,
            questionText = "Which region beyond Neptune is home to many icy objects, including Pluto?",
            options = listOf(
                "Asteroid Belt",
                "Oort Cloud",
                "Kuiper Belt",
                "Van Allen Belt"
            ),
            correctAnswerIndex = 2,
            explanation = "The Kuiper Belt is a region beyond Neptune containing many icy bodies."
        ),
        QuizQuestion(
            id = 11,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.EASY,
            questionText = "Which planet is known as the Red Planet?",
            options = listOf(
                "Mercury",
                "Mars",
                "Saturn",
                "Uranus"
            ),
            correctAnswerIndex = 1,
            explanation = "Mars appears reddish because of iron-rich dust on its surface."
        ),
        QuizQuestion(
            id = 12,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.MEDIUM,
            questionText = "Which factor most likely makes stable moons less common for Mercury and Venus?",
            options = listOf(
                "They are too close to the Sun",
                "They are made of gas",
                "Their moons all became rings",
                "They are outside the Solar System"
            ),
            correctAnswerIndex = 0,
            explanation = "Being closer to the Sun makes stable moon capture and long-term moon orbits harder for Mercury and Venus."
        ),
        QuizQuestion(
            id = 13,
            topic = QuizTopic.STARS_AND_GALAXIES,
            difficulty = Difficulty.EASY,
            questionText = "What is a galaxy?",
            options = listOf(
                "A single bright star",
                "A group of stars, gas, and dust held together by gravity",
                "A type of planet",
                "A cloud found only on Earth"
            ),
            correctAnswerIndex = 1,
            explanation = "Galaxies are massive systems of stars, gas, dust, and dark matter bound by gravity."
        ),
        QuizQuestion(
            id = 14,
            topic = QuizTopic.STARS_AND_GALAXIES,
            difficulty = Difficulty.MEDIUM,
            questionText = "Which stage comes first in the life cycle of a star like the Sun?",
            options = listOf(
                "White dwarf",
                "Neutron star",
                "Protostar",
                "Supernova"
            ),
            correctAnswerIndex = 2,
            explanation = "A star begins as a protostar, formed as gas and dust collapse under gravity."
        ),
        QuizQuestion(
            id = 15,
            topic = QuizTopic.SPACE_EXPLORATION,
            difficulty = Difficulty.EASY,
            questionText = "Who was the first human to travel into space?",
            options = listOf(
                "Neil Armstrong",
                "Yuri Gagarin",
                "Buzz Aldrin",
                "John Glenn"
            ),
            correctAnswerIndex = 1,
            explanation = "Yuri Gagarin orbited Earth in 1961 and became the first human in space."
        ),
        QuizQuestion(
            id = 16,
            topic = QuizTopic.SPACE_EXPLORATION,
            difficulty = Difficulty.MEDIUM,
            questionText = "What is the main purpose of a space telescope?",
            options = listOf(
                "To control weather on Earth",
                "To observe celestial objects above Earth's atmosphere",
                "To transport astronauts between planets",
                "To produce fuel for rockets"
            ),
            correctAnswerIndex = 1,
            explanation = "Space telescopes avoid atmospheric distortion and can observe wavelengths blocked from the ground."
        ),
        QuizQuestion(
            id = 17,
            topic = QuizTopic.SOLAR_SYSTEM,
            difficulty = Difficulty.EASY,
            questionText = "What is an asteroid?",
            options = listOf(
                "A small rocky body orbiting the Sun",
                "A star that exploded",
                "A moon with an atmosphere",
                "A cloud of gas around Earth"
            ),
            correctAnswerIndex = 0,
            explanation = "Asteroids are small rocky objects that orbit the Sun, mostly in the asteroid belt."
        ),
        QuizQuestion(
            id = 18,
            topic = QuizTopic.PLANETS,
            difficulty = Difficulty.MEDIUM,
            questionText = "Which planet rotates on its side compared with most other planets?",
            options = listOf(
                "Earth",
                "Neptune",
                "Uranus",
                "Mars"
            ),
            correctAnswerIndex = 2,
            explanation = "Uranus has an axial tilt of about 98 degrees, so it rotates nearly on its side."
        ),
        QuizQuestion(
            id = 19,
            topic = QuizTopic.STARS_AND_GALAXIES,
            difficulty = Difficulty.EASY,
            questionText = "What is a powerful stellar explosion called?",
            options = listOf(
                "Comet",
                "Supernova",
                "Asteroid",
                "Nebula"
            ),
            correctAnswerIndex = 1,
            explanation = "A supernova is a powerful stellar explosion at the end of a star's life cycle."
        ),
        QuizQuestion(
            id = 20,
            topic = QuizTopic.SPACE_EXPLORATION,
            difficulty = Difficulty.MEDIUM,
            questionText = "Why do rockets need multiple stages for many missions?",
            options = listOf(
                "To create artificial gravity",
                "To reduce mass by dropping empty fuel tanks",
                "To make more noise during launch",
                "To cool the spacecraft"
            ),
            correctAnswerIndex = 1,
            explanation = "Staging improves efficiency because empty tanks and engines are discarded to reduce mass."
        )
    )
}