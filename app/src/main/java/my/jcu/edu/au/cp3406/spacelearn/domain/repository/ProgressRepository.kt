package my.jcu.edu.au.cp3406.spacelearn.domain.repository

import kotlinx.coroutines.flow.Flow
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult

interface ProgressRepository {

    suspend fun saveQuizResult(
        result: QuizResult
    )

    fun observeQuizResults(): Flow<List<QuizResult>>

    suspend fun clearQuizResults()
}