package my.jcu.edu.au.cp3406.spacelearn.testdouble

import my.jcu.edu.au.cp3406.spacelearn.domain.model.AstronomyContent

object AstronomyTestFixtures {

    val cachedContent =
        AstronomyContent(
            date = "2026-07-19",
            title = "Cached Space Image",
            explanation =
                "This content was previously saved.",
            imageUrl =
                "https://example.com/cached.jpg",
            highDefinitionUrl =
                "https://example.com/cached-hd.jpg",
            mediaType = "image",
            copyright =
                "Cached Photographer"
        )

    val networkContent =
        AstronomyContent(
            date = "2026-07-20",
            title = "Today's Space Image",
            explanation =
                "This content was loaded from the network.",
            imageUrl =
                "https://example.com/network.jpg",
            highDefinitionUrl =
                "https://example.com/network-hd.jpg",
            mediaType = "image",
            copyright =
                "Network Photographer"
        )
}