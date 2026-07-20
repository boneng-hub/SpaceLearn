package my.jcu.edu.au.cp3406.spacelearn.testdouble

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizResult
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.ProgressRepository

class FakeProgressRepository :
    ProgressRepository {

    private val resultsFlow =
        MutableStateFlow<List<QuizResult>>(
            emptyList()
        )

    val savedResults: List<QuizResult>
        get() = resultsFlow.value

    var clearCallCount: Int = 0
        private set

    override suspend fun saveQuizResult(
        result: QuizResult
    ) {
        resultsFlow.value =
            listOf(result) + resultsFlow.value
    }

    override fun observeQuizResults():
            Flow<List<QuizResult>> {

        return resultsFlow.asStateFlow()
    }

    override suspend fun clearQuizResults() {
        clearCallCount += 1
        resultsFlow.value = emptyList()
    }
}