package my.jcu.edu.au.cp3406.spacelearn.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "astronomy_content")
data class AstronomyContentEntity(
    @PrimaryKey
    val date: String,
    val title: String,
    val explanation: String,
    val imageUrl: String?,
    val highDefinitionUrl: String?,
    val mediaType: String,
    val copyright: String?
)