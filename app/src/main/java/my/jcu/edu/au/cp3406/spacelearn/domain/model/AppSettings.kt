package my.jcu.edu.au.cp3406.spacelearn.domain.model

data class AppSettings(
    val defaultDifficulty: Difficulty = Difficulty.EASY,
    val defaultQuestionCount: Int = 3,
    val randomiseQuestions: Boolean = false
)