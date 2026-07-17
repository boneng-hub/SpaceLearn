package my.jcu.edu.au.cp3406.spacelearn.domain.repository

import kotlinx.coroutines.flow.Flow
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AppSettings
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty

interface SettingsRepository {

    val settings: Flow<AppSettings>

    suspend fun setDefaultDifficulty(
        difficulty: Difficulty
    )

    suspend fun setDefaultQuestionCount(
        questionCount: Int
    )

    suspend fun setRandomiseQuestions(
        enabled: Boolean
    )
}