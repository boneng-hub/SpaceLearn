package my.jcu.edu.au.cp3406.spacelearn.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApodDto(
    val date: String,
    val title: String,
    val explanation: String,
    val url: String,

    @SerializedName("hdurl")
    val hdUrl: String? = null,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String? = null,

    val copyright: String? = null
)