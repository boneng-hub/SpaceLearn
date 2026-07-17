package my.jcu.edu.au.cp3406.spacelearn.data.repository

import my.jcu.edu.au.cp3406.spacelearn.data.remote.NasaApodApi
import my.jcu.edu.au.cp3406.spacelearn.data.remote.dto.ApodDto
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository

class NetworkAstronomyRepository(
    private val nasaApodApi: NasaApodApi,
    private val apiKey: String
) : AstronomyRepository {

    override suspend fun getDailyContent():
            AstronomyContent {

        return nasaApodApi
            .getDailyContent(
                apiKey = apiKey
            )
            .toDomainModel()
    }
}

internal fun ApodDto.toDomainModel():
        AstronomyContent {

    val displayImageUrl =
        if (mediaType == "video") {
            thumbnailUrl
        } else {
            url
        }

    return AstronomyContent(
        date = date,
        title = title,
        explanation = explanation,
        imageUrl = displayImageUrl,
        highDefinitionUrl = hdUrl,
        mediaType = mediaType,
        copyright = copyright
    )
}