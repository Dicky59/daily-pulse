package com.dicky59.dailypulse.articles.presentation

import com.dicky59.dailypulse.ai.data.AiSummaryData

data class AiSummary(
  val summary: String,
  val sentiment: String,
) {
  companion object {
    fun mapAiSummary(aiSummaryData: AiSummaryData): AiSummary =
      AiSummary(
        summary = aiSummaryData.summary,
        sentiment = aiSummaryData.sentiment,
      )
  }
}
