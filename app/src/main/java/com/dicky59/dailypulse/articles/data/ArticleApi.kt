package com.dicky59.dailypulse.articles.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
  @GET("top-headlines")
  suspend fun getArticles(
    @Query("country") country: String = "us",
    @Query("category") category: String = "business",
    @Query("apiKey") apiKey: String,
  ): ArticlesResponse
}

data class ArticlesResponse(
  val status: String,
  val totalResults: Int,
  val articles: List<ArticleData>,
)
