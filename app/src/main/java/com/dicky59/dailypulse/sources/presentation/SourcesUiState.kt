package com.dicky59.dailypulse.sources.presentation

sealed class SourcesUiState {
  data object Loading : SourcesUiState()

  data class Success(
    val sources: List<Source>,
  ) : SourcesUiState()

  data class Error(
    val message: String,
  ) : SourcesUiState()
}
