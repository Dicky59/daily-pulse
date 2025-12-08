package com.dicky59.dailypulse.articles.data

import com.dicky59.dailypulse.articles.presentation.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleData(
  val title: String,
  val description: String?,
  val url: String,
  @Json(name = "urlToImage") val imageUrl: String?,
  @Json(name = "publishedAt") val date: String,
)

fun ArticleData.toArticle(): Article =
  Article(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    date = date,
  )
