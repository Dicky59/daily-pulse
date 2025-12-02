package com.dicky59.dailypulse.articles.data

import com.dicky59.dailypulse.BuildConfig
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val api: ArticleApi) {
    suspend fun getArticles(): List<ArticleData> {
        return api.getArticles(apiKey = BuildConfig.NEWS_API_KEY).articles
    }
}
