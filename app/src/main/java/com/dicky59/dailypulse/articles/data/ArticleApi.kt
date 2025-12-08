package com.dicky59.dailypulse.articles.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
  @GET("everything")
  suspend fun getArticles(
          @Query("q") query: String = "news",
          @Query("language") language: String = "en",
          @Query("apiKey") apiKey: String,
  ): ArticlesResponse
}

data class ArticlesResponse(
        val status: String,
        val totalResults: Int,
        val articles: List<ArticleData>,
)
