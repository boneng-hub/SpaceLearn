package my.jcu.edu.au.cp3406.spacelearn

import android.content.Context
import androidx.room.Room
import my.jcu.edu.au.cp3406.spacelearn.data.local.SpaceLearnDatabase
import my.jcu.edu.au.cp3406.spacelearn.data.repository.LocalQuizRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.RoomProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.DataStoreSettingsRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository
class AppContainer(
    context: Context
) {
    private val database: SpaceLearnDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            SpaceLearnDatabase::class.java,
            DATABASE_NAME
        ).build()

    val quizRepository: QuizRepository =
        LocalQuizRepository()

    val progressRepository: ProgressRepository =
        RoomProgressRepository(
            quizResultDao = database.quizResultDao()
        )

    val settingsRepository: SettingsRepository =
        DataStoreSettingsRepository(
            context = context.applicationContext
        )
    private companion object {
        const val DATABASE_NAME =
            "spacelearn_database"
    }
}