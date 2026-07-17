package my.jcu.edu.au.cp3406.spacelearn.data.remote

import my.jcu.edu.au.cp3406.spacelearn.data.remote.dto.ApodDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApodApi {

    @GET("planetary/apod")
    suspend fun getDailyContent(
        @Query("api_key")
        apiKey: String,

        @Query("thumbs")
        includeVideoThumbnail: Boolean = true
    ): ApodDto
}