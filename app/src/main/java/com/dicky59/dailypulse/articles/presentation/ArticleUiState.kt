package com.dicky59.dailypulse.articles.presentation

sealed class ArticleUiState {
  data object Loading : ArticleUiState()

  data class Success(
    val articles: List<Article>,
    val aiSummary: AiSummary? = null,
  ) : ArticleUiState()

  data class Error(
    val message: String,
  ) : ArticleUiState()
}
