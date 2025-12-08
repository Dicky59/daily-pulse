package com.dicky59.dailypulse.sources.data

import com.dicky59.dailypulse.BuildConfig
import javax.inject.Inject

class SourceRepository
  @Inject
  constructor(
    private val api: SourceApi,
  ) {
    suspend fun getSources(): List<SourceData> = api.getSources(apiKey = BuildConfig.NEWS_API_KEY).sources
  }
