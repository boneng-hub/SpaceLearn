package my.jcu.edu.au.cp3406.spacelearn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import my.jcu.edu.au.cp3406.spacelearn.data.repository.DataStoreSettingsRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.DefaultAstronomyRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.LocalQuizRepository
import my.jcu.edu.au.cp3406.spacelearn.data.repository.RoomProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.SettingsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        implementation: LocalQuizRepository
    ): QuizRepository

    @Binds
    @Singleton
    abstract fun bindProgressRepository(
        implementation: RoomProgressRepository
    ): ProgressRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        implementation:
        DataStoreSettingsRepository
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindAstronomyRepository(
        implementation:
        DefaultAstronomyRepository
    ): AstronomyRepository
}