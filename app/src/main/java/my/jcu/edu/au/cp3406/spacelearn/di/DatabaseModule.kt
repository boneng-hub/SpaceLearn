package my.jcu.edu.au.cp3406.spacelearn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import my.jcu.edu.au.cp3406.spacelearn.data.local.MIGRATION_1_2
import my.jcu.edu.au.cp3406.spacelearn.data.local.SpaceLearnDatabase
import my.jcu.edu.au.cp3406.spacelearn.data.local.dao.AstronomyContentDao
import my.jcu.edu.au.cp3406.spacelearn.data.local.dao.QuizResultDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSpaceLearnDatabase(
        @ApplicationContext context: Context
    ): SpaceLearnDatabase {
        return Room.databaseBuilder(
            context,
            SpaceLearnDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideQuizResultDao(
        database: SpaceLearnDatabase
    ): QuizResultDao {
        return database.quizResultDao()
    }

    @Provides
    fun provideAstronomyContentDao(
        database: SpaceLearnDatabase
    ): AstronomyContentDao {
        return database.astronomyContentDao()
    }

    private const val DATABASE_NAME =
        "spacelearn_database"
}