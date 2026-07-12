package my.jcu.edu.au.cp3406.spacelearn.domain.model

data class QuizQuestion(
    val id: Int,
    val topic: QuizTopic,
    val difficulty: Difficulty,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
) {
    init {
        require(options.size == 4) {
            "Each quiz question must have exactly four options."
        }

        require(correctAnswerIndex in options.indices) {
            "The correct answer index must match one of the options."
        }
    }
}