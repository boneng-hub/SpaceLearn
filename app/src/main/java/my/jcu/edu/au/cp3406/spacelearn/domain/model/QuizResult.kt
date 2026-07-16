package my.jcu.edu.au.cp3406.spacelearn.domain.model

data class QuizResult(
    val id: Long = 0,
    val topic: QuizTopic,
    val difficulty: Difficulty,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val completedAt: Long
) {
    val accuracy: Int
        get() {
            if (totalQuestions == 0) {
                return 0
            }

            return correctAnswers * 100 / totalQuestions
        }
}