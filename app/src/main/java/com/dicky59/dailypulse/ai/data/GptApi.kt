package com.dicky59.dailypulse.ai.data

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GptApi {
  @POST("chat/completions")
  suspend fun getArticleSummary(
    @Header("Authorization") apiKey: String,
    @Body request: GptRequest,
  ): GptResponse
}

data class GptRequest(
  val model: String,
  val messages: List<GptMessage>,
)

data class GptMessage(
  val role: String, // "system", "user", "assistant"
  val content: String,
)

data class GptResponse(
  val id: String,
  val choices: List<GptChoice>,
)

data class GptChoice(
  val index: Int,
  val message: GptMessage,
)
