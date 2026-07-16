package my.jcu.edu.au.cp3406.spacelearn.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.QuizResultEntity

@Dao
interface QuizResultDao {

    @Insert
    suspend fun insertResult(
        result: QuizResultEntity
    ): Long

    @Query(
        """
        SELECT * FROM quiz_results
        ORDER BY completedAt DESC
        """
    )
    fun observeAllResults(): Flow<List<QuizResultEntity>>

    @Query("DELETE FROM quiz_results")
    suspend fun clearAllResults()
}