package eu.tutorials.newsapp.models

data class NewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Articles>? = null
)
