package my.jcu.edu.au.cp3406.spacelearn.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(
        db: SupportSQLiteDatabase
    ) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `astronomy_content` (
                `date` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `explanation` TEXT NOT NULL,
                `imageUrl` TEXT,
                `highDefinitionUrl` TEXT,
                `mediaType` TEXT NOT NULL,
                `copyright` TEXT,
                PRIMARY KEY(`date`)
            )
            """.trimIndent()
        )
    }
}