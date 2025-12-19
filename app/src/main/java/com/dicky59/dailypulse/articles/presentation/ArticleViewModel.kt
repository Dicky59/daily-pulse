package com.dicky59.dailypulse.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicky59.dailypulse.ai.data.AiRepository
import com.dicky59.dailypulse.articles.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel
  @Inject
  constructor(
    private val articleRepository: ArticleRepository,
    private val aiRepository: AiRepository,
  ) : ViewModel() {
    private val _uiState = MutableStateFlow<ArticleUiState>(ArticleUiState.Loading)
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()

    init {
      loadArticles()
    }

    private fun loadArticles() {
      viewModelScope.launch {
        _uiState.value = ArticleUiState.Loading
        logState("Loading", null)

        try {
          val articlesData = articleRepository.getArticles()
          val articles = articlesData.map { Article.mapArticle(it) }
          _uiState.value = ArticleUiState.Success(articles)
          logState("Success", articles)
        } catch (e: Exception) {
          val userFriendlyMessage = "Something went wrong, please try again later"
          _uiState.value = ArticleUiState.Error(userFriendlyMessage)
          logState("Error", userFriendlyMessage)
          Timber.e(e, "Error loading articles: ${e.message}")
        }
      }
    }

    fun getAiSummary() {
      viewModelScope.launch {
        val currentState = _uiState.value
        if (currentState !is ArticleUiState.Success) {
          return@launch
        }

        try {
          val articleTitles = currentState.articles.map { it.title }
          val aiSummaryData = aiRepository.getArticleSummary(articleTitles)
          val aiSummary = AiSummary.mapAiSummary(aiSummaryData)

          _uiState.value = currentState.copy(aiSummary = aiSummary)
          Timber.d("ArticleUiState: AI Summary loaded - Sentiment: ${aiSummary.sentiment}")
        } catch (e: Exception) {
          Timber.e(e, "Error loading AI summary: ${e.message}")
        }
      }
    }

    fun dismissAiSummary() {
      val currentState = _uiState.value
      if (currentState is ArticleUiState.Success) {
        _uiState.value = currentState.copy(aiSummary = null)
      }
    }

    private fun logState(
      state: String,
      data: Any?,
    ) {
      when (data) {
        is List<*> -> Timber.d("ArticleUiState: $state - Articles count: ${data.size}")
        is String -> Timber.d("ArticleUiState: $state - Message: $data")
        null -> Timber.d("ArticleUiState: $state")
      }
    }
  }
