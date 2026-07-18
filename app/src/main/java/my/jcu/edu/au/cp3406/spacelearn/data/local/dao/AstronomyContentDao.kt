package my.jcu.edu.au.cp3406.spacelearn.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.AstronomyContentEntity

@Dao
interface AstronomyContentDao {

    @Query(
        """
        SELECT * FROM astronomy_content
        ORDER BY date DESC
        LIMIT 1
        """
    )
    fun observeLatestContent():
            Flow<AstronomyContentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(
        content: AstronomyContentEntity
    )
}