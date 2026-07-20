package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic

object QuizSetupTestTags {

    const val SCREEN_LIST =
        "quiz_setup_screen_list"

    const val RANDOMISE_SWITCH =
        "quiz_setup_randomise_switch"

    const val BEGIN_BUTTON =
        "quiz_setup_begin_button"

    fun topic(topic: QuizTopic): String {
        return "quiz_setup_topic_${topic.name}"
    }

    fun difficulty(
        difficulty: Difficulty
    ): String {
        return "quiz_setup_difficulty_${difficulty.name}"
    }

    fun questionCount(
        count: Int
    ): String {
        return "quiz_setup_count_$count"
    }
}