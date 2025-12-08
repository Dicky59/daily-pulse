package com.dicky59.dailypulse.sources.presentation

import com.dicky59.dailypulse.sources.data.SourceData

data class Source(
  val id: String,
  val name: String,
  val description: String,
) {
  companion object {
    fun mapSource(sourceData: SourceData): Source =
      Source(
        id = sourceData.id,
        name = sourceData.name,
        description = sourceData.description,
      )
  }
}
