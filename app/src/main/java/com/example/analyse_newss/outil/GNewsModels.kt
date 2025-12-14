package com.example.analyse_newss.outil

import com.google.gson.annotations.SerializedName

data class GNewsResponse(
    val totalArticles: Int,
    val articles: List<GNewsArticle>
)

data class GNewsArticle(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: GNewsSource
)

data class GNewsSource(
    val name: String,
    val url: String
)
