package my.jcu.edu.au.cp3406.spacelearn.ui.settings

import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty

object SettingsScreenTestTags {

    const val SCREEN_LIST =
        "settings_screen_list"

    const val RANDOMISE_SWITCH =
        "settings_randomise_switch"

    const val CLEAR_HISTORY_BUTTON =
        "settings_clear_history_button"

    const val CLEAR_DIALOG =
        "settings_clear_dialog"

    const val CLEAR_DIALOG_CONFIRM =
        "settings_clear_dialog_confirm"

    const val CLEAR_DIALOG_CANCEL =
        "settings_clear_dialog_cancel"

    fun difficulty(
        difficulty: Difficulty
    ): String {
        return "settings_difficulty_${difficulty.name}"
    }

    fun questionCount(
        count: Int
    ): String {
        return "settings_question_count_$count"
    }
}

