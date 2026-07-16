package my.jcu.edu.au.cp3406.spacelearn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import my.jcu.edu.au.cp3406.spacelearn.data.local.dao.QuizResultDao
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.QuizResultEntity

@Database(
    entities = [
        QuizResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SpaceLearnDatabase : RoomDatabase() {

    abstract fun quizResultDao(): QuizResultDao
}