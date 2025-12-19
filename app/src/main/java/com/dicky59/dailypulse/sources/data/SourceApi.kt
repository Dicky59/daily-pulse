package com.dicky59.dailypulse.sources.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {
  @GET("top-headlines/sources")
  suspend fun getSources(
    @Query("language") language: String = "en",
    @Query("apiKey") apiKey: String,
  ): SourcesResponse
}

data class SourcesResponse(
  val status: String,
  val sources: List<SourceData>,
)
