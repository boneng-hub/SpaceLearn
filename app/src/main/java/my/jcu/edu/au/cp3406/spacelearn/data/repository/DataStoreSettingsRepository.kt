package my.jcu.edu.au.cp3406.spacelearn.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import my.jcu.edu.au.cp3406.spacelearn.data.preferences.settingsDataStore
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AppSettings
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
) : SettingsRepository {

    override val settings: Flow<AppSettings> =
        context.settingsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val savedDifficulty =
                    preferences[SettingsKeys.DEFAULT_DIFFICULTY]

                val difficulty =
                    Difficulty.entries.firstOrNull {
                        it.name == savedDifficulty
                    } ?: Difficulty.EASY

                val savedQuestionCount =
                    preferences[SettingsKeys.DEFAULT_QUESTION_COUNT]

                val questionCount =
                    if (savedQuestionCount == 5) {
                        5
                    } else {
                        3
                    }

                AppSettings(
                    defaultDifficulty = difficulty,
                    defaultQuestionCount = questionCount,
                    randomiseQuestions =
                        preferences[
                            SettingsKeys.RANDOMISE_QUESTIONS
                        ] ?: false
                )
            }

    override suspend fun setDefaultDifficulty(
        difficulty: Difficulty
    ) {
        context.settingsDataStore.edit { preferences ->
            preferences[
                SettingsKeys.DEFAULT_DIFFICULTY
            ] = difficulty.name
        }
    }

    override suspend fun setDefaultQuestionCount(
        questionCount: Int
    ) {
        val validQuestionCount =
            if (questionCount == 5) 5 else 3

        context.settingsDataStore.edit { preferences ->
            preferences[
                SettingsKeys.DEFAULT_QUESTION_COUNT
            ] = validQuestionCount
        }
    }

    override suspend fun setRandomiseQuestions(
        enabled: Boolean
    ) {
        context.settingsDataStore.edit { preferences ->
            preferences[
                SettingsKeys.RANDOMISE_QUESTIONS
            ] = enabled
        }
    }

    private object SettingsKeys {
        val DEFAULT_DIFFICULTY =
            stringPreferencesKey(
                "default_difficulty"
            )

        val DEFAULT_QUESTION_COUNT =
            intPreferencesKey(
                "default_question_count"
            )

        val RANDOMISE_QUESTIONS =
            booleanPreferencesKey(
                "randomise_questions"
            )
    }
}