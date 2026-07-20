package my.jcu.edu.au.cp3406.spacelearn.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.QuizResultEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpaceLearnMigrationTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context =
            ApplicationProvider
                .getApplicationContext()

        context.deleteDatabase(TEST_DATABASE)
    }

    @After
    fun tearDown() {
        context.deleteDatabase(TEST_DATABASE)
    }

    @Test
    fun migration1To2_preservesQuizResultAndCreatesAstronomyTable() =
        runTest {
            createVersionOneDatabase()

            val versionTwoDatabase =
                Room.databaseBuilder(
                    context,
                    SpaceLearnDatabase::class.java,
                    TEST_DATABASE
                )
                    .addMigrations(
                        MIGRATION_1_2
                    )
                    .build()

            try {
                val results =
                    versionTwoDatabase
                        .quizResultDao()
                        .observeAllResults()
                        .first()

                assertEquals(
                    1,
                    results.size
                )

                val savedResult =
                    results.single()

                assertEquals(
                    1L,
                    savedResult.id
                )

                assertEquals(
                    "PLANETS",
                    savedResult.topic
                )

                assertEquals(
                    "EASY",
                    savedResult.difficulty
                )

                assertEquals(
                    3,
                    savedResult.totalQuestions
                )

                assertEquals(
                    2,
                    savedResult.correctAnswers
                )

                assertEquals(
                    TEST_COMPLETION_TIME,
                    savedResult.completedAt
                )

                val astronomyContent =
                    versionTwoDatabase
                        .astronomyContentDao()
                        .observeLatestContent()
                        .first()

                assertNull(astronomyContent)

                assertTrue(
                    versionTwoDatabase
                        .openHelper
                        .readableDatabase
                        .isOpen
                )
            } finally {
                versionTwoDatabase.close()
            }
        }

    private suspend fun createVersionOneDatabase() {
        val versionOneDatabase =
            Room.databaseBuilder(
                context,
                SpaceLearnDatabaseV1::class.java,
                TEST_DATABASE
            ).build()

        try {
            versionOneDatabase
                .quizResultDao()
                .insertResult(
                    QuizResultEntity(
                        id = 1L,
                        topic = "PLANETS",
                        difficulty = "EASY",
                        totalQuestions = 3,
                        correctAnswers = 2,
                        completedAt =
                            TEST_COMPLETION_TIME
                    )
                )
        } finally {
            versionOneDatabase.close()
        }
    }

    private companion object {
        const val TEST_DATABASE =
            "spacelearn-migration-test"

        const val TEST_COMPLETION_TIME =
            1_700_000_000_000L
    }
}