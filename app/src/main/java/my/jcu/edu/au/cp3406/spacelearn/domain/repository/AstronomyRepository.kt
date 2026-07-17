package my.jcu.edu.au.cp3406.spacelearn.domain.repository

import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent

interface AstronomyRepository {

    suspend fun getDailyContent():
            AstronomyContent
}