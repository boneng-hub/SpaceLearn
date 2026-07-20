package my.jcu.edu.au.cp3406.spacelearn.testdouble

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

class FakeAstronomyRepository(
    initialContent: AstronomyContent? = null
) : AstronomyRepository {

    private val contentFlow =
        MutableStateFlow(initialContent)

    var remoteContent: AstronomyContent? = null

    var refreshException: Exception? = null

    var refreshCallCount: Int = 0
        private set

    override fun observeLatestContent():
            Flow<AstronomyContent?> {

        return contentFlow.asStateFlow()
    }

    override suspend fun refreshDailyContent() {
        refreshCallCount += 1

        refreshException?.let { exception ->
            throw exception
        }

        remoteContent?.let { content ->
            contentFlow.value = content
        }
    }

    fun emitContent(
        content: AstronomyContent?
    ) {
        contentFlow.value = content
    }
}