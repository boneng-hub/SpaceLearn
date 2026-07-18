package my.jcu.edu.au.cp3406.spacelearn

import android.content.Context
import androidx.room.Room
import my.jcu.edu.au.cp3406.spacelearn.data.local.MIGRATION_1_2
import my.jcu.edu.au.cp3406.spacelearn.data.local.SpaceLearnDatabase
import my.jcu.edu.au.cp3406.spacelearn.data.repository.LocalQuizRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.RoomProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.DataStoreSettingsRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository
import my.jcu.edu.au.cp3406.spacelearn.data.remote.NasaApodApi
import my.jcu.edu.au.cp3406.spacelearn.data.repository.DefaultAstronomyRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class AppContainer(
    context: Context
) {
    private val database: SpaceLearnDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            SpaceLearnDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()

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

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    private val nasaApodApi: NasaApodApi =
        retrofit.create(
            NasaApodApi::class.java
        )

    val astronomyRepository: AstronomyRepository =
        DefaultAstronomyRepository(
            nasaApodApi = nasaApodApi,
            astronomyContentDao = database.astronomyContentDao(),
            apiKey = BuildConfig.NASA_API_KEY
        )

    private companion object {
        const val DATABASE_NAME =
            "spacelearn_database"
        private const val NASA_BASE_URL =
            "https://api.nasa.gov/"
    }
}