package my.jcu.edu.au.cp3406.spacelearn.domain.model

/**
 * Represents a quiz topic (e.g. "Solar System", "Black Holes").
 * TODO: Load from a data source.
 */
data class QuizTopic(
    val id: Int = 0,
    val name: String = "",
    val description: String = ""
)

