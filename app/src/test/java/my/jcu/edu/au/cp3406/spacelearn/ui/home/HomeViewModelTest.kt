package my.jcu.edu.au.cp3406.spacelearn.ui.home

import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import my.jcu.edu.au.cp3406.spacelearn.testdouble.AstronomyTestFixtures
import my.jcu.edu.au.cp3406.spacelearn.testdouble.FakeAstronomyRepository
import my.jcu.edu.au.cp3406.spacelearn.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule =
        MainDispatcherRule()

    @Test
    fun initialLoad_success_displaysNetworkContent() =
        runTest {
            val repository =
                FakeAstronomyRepository().apply {
                    remoteContent =
                        AstronomyTestFixtures
                            .networkContent
                }

            val viewModel =
                HomeViewModel(
                    astronomyRepository =
                        repository
                )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertEquals(
                AstronomyTestFixtures.networkContent,
                state.dailyContent
            )

            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertFalse(
                state.isUsingCachedContent
            )

            assertNull(state.errorMessage)

            assertEquals(
                1,
                repository.refreshCallCount
            )
        }

    @Test
    fun initialLoad_failureWithCache_displaysCachedContent() =
        runTest {
            val repository =
                FakeAstronomyRepository(
                    initialContent =
                        AstronomyTestFixtures
                            .cachedContent
                ).apply {
                    refreshException =
                        IOException(
                            "No internet connection"
                        )
                }

            val viewModel =
                HomeViewModel(
                    astronomyRepository =
                        repository
                )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertEquals(
                AstronomyTestFixtures.cachedContent,
                state.dailyContent
            )

            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertTrue(
                state.isUsingCachedContent
            )

            assertNotNull(state.errorMessage)

            assertTrue(
                state.errorMessage!!.contains(
                    "saved",
                    ignoreCase = true
                )
            )
        }

    @Test
    fun initialLoad_failureWithoutCache_displaysError() =
        runTest {
            val repository =
                FakeAstronomyRepository().apply {
                    refreshException =
                        IOException(
                            "No internet connection"
                        )
                }

            val viewModel =
                HomeViewModel(
                    astronomyRepository =
                        repository
                )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertNull(state.dailyContent)

            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertFalse(
                state.isUsingCachedContent
            )

            assertNotNull(state.errorMessage)

            assertTrue(
                state.errorMessage!!.contains(
                    "connection",
                    ignoreCase = true
                )
            )
        }

    @Test
    fun retryAfterFailure_successfullyLoadsContent() =
        runTest {
            val repository =
                FakeAstronomyRepository().apply {
                    refreshException =
                        IOException(
                            "No internet connection"
                        )
                }

            val viewModel =
                HomeViewModel(
                    astronomyRepository =
                        repository
                )

            advanceUntilIdle()

            assertNull(
                viewModel.uiState.value.dailyContent
            )

            assertNotNull(
                viewModel.uiState.value.errorMessage
            )

            repository.refreshException = null
            repository.remoteContent =
                AstronomyTestFixtures
                    .networkContent

            viewModel.refreshDailyContent()

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertEquals(
                AstronomyTestFixtures.networkContent,
                state.dailyContent
            )

            assertNull(state.errorMessage)
            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertFalse(
                state.isUsingCachedContent
            )

            assertEquals(
                2,
                repository.refreshCallCount
            )
        }
}