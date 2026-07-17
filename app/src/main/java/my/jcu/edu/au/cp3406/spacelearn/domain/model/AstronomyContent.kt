package my.jcu.edu.au.cp3406.spacelearn.domain.model

data class AstronomyContent(
    val date: String,
    val title: String,
    val explanation: String,
    val imageUrl: String?,
    val highDefinitionUrl: String?,
    val mediaType: String,
    val copyright: String?
) {
    val isImage: Boolean
        get() = mediaType == "image"

    val sourceLabel: String
        get() {
            return copyright?.let {
                "NASA APOD · $it"
            } ?: "NASA Astronomy Picture of the Day"
        }
}