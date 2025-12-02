package com.dicky59.dailypulse.articles.presentation

sealed class ArticleUiState {
  data object Loading : ArticleUiState()
  data class Success(val articles: List<Article>) : ArticleUiState()
  data class Error(val message: String) : ArticleUiState()
}
