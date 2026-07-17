package my.jcu.edu.au.cp3406.spacelearn.domain.model

data class QuizConfig(
    val topic: QuizTopic,
    val difficulty: Difficulty,
    val questionCount: Int,
    val randomiseQuestions: Boolean = false
) {
    init {
        require(questionCount == 3 || questionCount == 5) {
            "Question count must be either 3 or 5."
        }
    }
}