package com.dicky59.dailypulse.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicky59.dailypulse.articles.data.ArticleData
import com.dicky59.dailypulse.articles.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
        ViewModel() {

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
        val articles = articlesData.map { articleData -> articleData.toArticleWithFormattedDate() }
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

  private fun ArticleData.toArticleWithFormattedDate(): Article {
    return Article(
            title = title,
            description = description ?: "",
            url = url,
            imageUrl = imageUrl ?: "",
            date = formatDate(date)
    )
  }

  private fun formatDate(dateString: String): String {
    return try {
      val articleDate = Instant.parse(dateString).atZone(ZoneId.systemDefault()).toLocalDate()

      val today = LocalDate.now()
      val daysDifference = ChronoUnit.DAYS.between(articleDate, today).toInt()

      when {
        daysDifference == 0 -> "Today"
        daysDifference == 1 -> "Yesterday"
        else -> "$daysDifference days ago"
      }
    } catch (e: Exception) {
      Timber.e(e, "Error parsing date: $dateString")
      dateString
    }
  }

  private fun logState(state: String, data: Any?) {
    when (data) {
      is List<*> -> Timber.d("ArticleUiState: $state - Articles count: ${data.size}")
      is String -> Timber.d("ArticleUiState: $state - Message: $data")
      null -> Timber.d("ArticleUiState: $state")
    }
  }
}
