package my.jcu.edu.au.cp3406.spacelearn.domain.repository

import kotlinx.coroutines.flow.Flow
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent

interface AstronomyRepository {

    fun observeLatestContent():
            Flow<AstronomyContent?>

    suspend fun refreshDailyContent()
}