package com.harol.newsfeed.data.enums

import com.harol.newsfeed.data.enums.ArticleCategory.*


enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun allArticleCategories(): List<ArticleCategory> {
    return listOf(
        BUSINESS,
        ENTERTAINMENT,
        GENERAL,
        HEALTH,
        SCIENCE,
        SPORTS,
        TECHNOLOGY
    )
}

fun String.toArticleCategory(): ArticleCategory? {
    val map = ArticleCategory.values().associateBy(ArticleCategory::categoryName)
    return map[this]
}