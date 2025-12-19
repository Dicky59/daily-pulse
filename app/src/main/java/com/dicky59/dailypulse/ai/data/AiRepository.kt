package com.dicky59.dailypulse.ai.data

import com.dicky59.dailypulse.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiRepository
  @Inject
  constructor(
    private val gptApi: GptApi,
  ) {
    suspend fun getArticleSummary(articleTitles: List<String>): AiSummaryData {
      if (BuildConfig.CHATGPT_API_KEY.isEmpty()) {
        return AiSummaryData(
          summary = "It was a good business day with no significant news.",
          sentiment = "Good",
        )
      }

      val request =
        GptRequest(
          model = "gpt-4o-mini",
          listOf(
            GptMessage(
              "system",
              "You are a helpful assistant that summarizes news and analyzes investing sentiment.",
            ),
            GptMessage(
              "user",
              """
              Based on the following article titles, please do two things:

              1. Provide a short, high-level summary of the day.
              2. Indicate the overall investing sentiment and whether it is positive, negative or neutral.

              Format your response strictly as valid JSON like this:

              {
                "summary": "Your summary here",
                "sentiment": "Your sentiment here"
              }

              Here are the article titles:
              ${articleTitles.joinToString("\n")}
              """.trimIndent(),
            ),
          ),
        )
      val response =
        gptApi.getArticleSummary(
          request = request,
          apiKey = BuildConfig.CHATGPT_API_KEY,
        )
      val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

      val adapter = moshi.adapter(AiSummaryData::class.java)

      return adapter.fromJson(response.choices[0].message.content)!!
    }
  }
