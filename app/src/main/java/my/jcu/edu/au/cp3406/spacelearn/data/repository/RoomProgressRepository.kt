package my.jcu.edu.au.cp3406.spacelearn.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import my.jcu.edu.au.cp3406.spacelearn.data.local.dao.QuizResultDao
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.QuizResultEntity
import my.jcu.edu.au.cp3406.spacelearn.domain.model.Difficulty
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizTopic
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository
import javax.inject.Inject

class RoomProgressRepository @Inject constructor(
    private val quizResultDao: QuizResultDao
) : ProgressRepository {

    override suspend fun saveQuizResult(
        result: QuizResult
    ) {
        quizResultDao.insertResult(
            result.toEntity()
        )
    }

    override fun observeQuizResults():
            Flow<List<QuizResult>> {

        return quizResultDao
            .observeAllResults()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomainModel()
                }
            }
    }

    override suspend fun clearQuizResults() {
        quizResultDao.clearAllResults()
    }
}

private fun QuizResult.toEntity(): QuizResultEntity {
    return QuizResultEntity(
        id = id,
        topic = topic.name,
        difficulty = difficulty.name,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        completedAt = completedAt
    )
}

private fun QuizResultEntity.toDomainModel(): QuizResult {
    return QuizResult(
        id = id,
        topic = QuizTopic.entries.firstOrNull {
            it.name == topic
        } ?: QuizTopic.SOLAR_SYSTEM,
        difficulty = Difficulty.entries.firstOrNull {
            it.name == difficulty
        } ?: Difficulty.EASY,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        completedAt = completedAt
    )
}