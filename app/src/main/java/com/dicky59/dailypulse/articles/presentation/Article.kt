package com.dicky59.dailypulse.articles.presentation

import com.dicky59.dailypulse.articles.data.ArticleData
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

data class Article(
  val title: String,
  val description: String,
  val url: String,
  val imageUrl: String,
  val date: String,
) {
  companion object {
    fun mapArticle(articleData: ArticleData): Article =
      Article(
        title = articleData.title,
        description = articleData.description ?: "",
        url = articleData.url,
        imageUrl = articleData.imageUrl ?: "",
        date = formatDate(articleData.date),
      )

    private fun formatDate(dateString: String): String =
      try {
        val articleDate =
          Instant
            .parse(dateString)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now()
        val daysDifference =
          ChronoUnit.DAYS.between(articleDate, today).toInt()

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
}
