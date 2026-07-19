package my.jcu.edu.au.cp3406.spacelearn.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import my.jcu.edu.au.cp3406.spacelearn.data.local.dao.AstronomyContentDao
import my.jcu.edu.au.cp3406.spacelearn.data.local.entity.AstronomyContentEntity
import my.jcu.edu.au.cp3406.spacelearn.data.remote.NasaApodApi
import my.jcu.edu.au.cp3406.spacelearn.data.remote.dto.ApodDto
import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.AstronomyRepository
import javax.inject.Inject
import javax.inject.Named
import my.jcu.edu.au.cp3406.spacelearn.di.NetworkModule

class DefaultAstronomyRepository @Inject constructor(
    private val nasaApodApi: NasaApodApi,
    private val astronomyContentDao:
    AstronomyContentDao,
    @Named(NetworkModule.NASA_API_KEY_NAME)
    private val apiKey: String
) : AstronomyRepository {

    override fun observeLatestContent():
            Flow<AstronomyContent?> {

        return astronomyContentDao
            .observeLatestContent()
            .map { entity ->
                entity?.toDomainModel()
            }
    }

    override suspend fun refreshDailyContent() {
        val content = nasaApodApi
            .getDailyContent(
                apiKey = apiKey
            )
            .toDomainModel()

        astronomyContentDao.insertContent(
            content.toEntity()
        )
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

private fun AstronomyContent.toEntity():
        AstronomyContentEntity {

    return AstronomyContentEntity(
        date = date,
        title = title,
        explanation = explanation,
        imageUrl = imageUrl,
        highDefinitionUrl = highDefinitionUrl,
        mediaType = mediaType,
        copyright = copyright
    )
}

private fun AstronomyContentEntity.toDomainModel():
        AstronomyContent {

    return AstronomyContent(
        date = date,
        title = title,
        explanation = explanation,
        imageUrl = imageUrl,
        highDefinitionUrl = highDefinitionUrl,
        mediaType = mediaType,
        copyright = copyright
    )
}