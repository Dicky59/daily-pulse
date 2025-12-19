package com.dicky59.dailypulse.sources.data

import com.dicky59.dailypulse.sources.presentation.Source
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceData(
  val id: String,
  val name: String,
  val description: String,
)

fun SourceData.toSource(): Source =
  Source(
    id = id,
    name = name,
    description = description,
  )
