package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

object QuizScreenTestTags {

    const val SCREEN =
        "quiz_screen"

    const val PROGRESS =
        "quiz_progress"

    const val QUESTION_TEXT =
        "quiz_question_text"

    const val FEEDBACK =
        "quiz_feedback"

    const val EXPLANATION =
        "quiz_explanation"

    const val NEXT_BUTTON =
        "quiz_next_button"

    fun answer(index: Int): String {
        return "quiz_answer_$index"
    }
}