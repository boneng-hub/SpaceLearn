package my.jcu.edu.au.cp3406.spacelearn.data.repository

import my.jcu.edu.au.cp3406.spacelearn.data.remote.dto.ApodDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ApodMapperTest {

    @Test
    fun imageResponse_usesStandardImageUrl() {
        val dto = ApodDto(
            date = "2026-07-17",
            title = "Test image",
            explanation = "Test explanation",
            url = "https://example.com/image.jpg",
            hdUrl =
                "https://example.com/image-hd.jpg",
            mediaType = "image"
        )

        val content = dto.toDomainModel()

        assertEquals(
            dto.url,
            content.imageUrl
        )

        assertEquals(
            dto.hdUrl,
            content.highDefinitionUrl
        )
    }

    @Test
    fun videoResponse_usesThumbnailUrl() {
        val dto = ApodDto(
            date = "2026-07-17",
            title = "Test video",
            explanation = "Test explanation",
            url = "https://example.com/video",
            mediaType = "video",
            thumbnailUrl =
                "https://example.com/thumbnail.jpg"
        )

        val content = dto.toDomainModel()

        assertEquals(
            dto.thumbnailUrl,
            content.imageUrl
        )
    }

    @Test
    fun videoWithoutThumbnail_returnsNullImageUrl() {
        val dto = ApodDto(
            date = "2026-07-17",
            title = "Test video",
            explanation = "Test explanation",
            url = "https://example.com/video",
            mediaType = "video",
            thumbnailUrl = null
        )

        val content = dto.toDomainModel()

        assertNull(content.imageUrl)
    }
}