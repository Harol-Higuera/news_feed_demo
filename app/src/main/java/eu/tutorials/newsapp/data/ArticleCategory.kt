package eu.tutorials.newsapp.data

import eu.tutorials.newsapp.data.ArticleCategory.*

enum class ArticleCategory(val category: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun allArticleCategories(): List<ArticleCategory> {
    return listOf(BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY)
}

fun String.toArticleCategory(): ArticleCategory? {
    val map = ArticleCategory.values().associateBy(ArticleCategory::category)
    return map[this]
}