package my.jcu.edu.au.cp3406.spacelearn.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val difficulty: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val completedAt: Long
)