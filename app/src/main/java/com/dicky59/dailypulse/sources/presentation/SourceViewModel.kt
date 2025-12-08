package com.dicky59.dailypulse.sources.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicky59.dailypulse.sources.data.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SourceViewModel
  @Inject
  constructor(
    private val sourceRepository: SourceRepository,
  ) : ViewModel() {
    private val _uiState = MutableStateFlow<SourcesUiState>(SourcesUiState.Loading)
    val uiState: StateFlow<SourcesUiState> = _uiState.asStateFlow()

    init {
      loadSources()
    }

    private fun loadSources() {
      viewModelScope.launch {
        _uiState.value = SourcesUiState.Loading
        logState("Loading", null)

        try {
          val sourcesData = sourceRepository.getSources()
          val sources = sourcesData.map { Source.mapSource(it) }
          _uiState.value = SourcesUiState.Success(sources)
          logState("Success", sources)
        } catch (e: Exception) {
          val userFriendlyMessage = "Something went wrong, please try again later"
          _uiState.value = SourcesUiState.Error(userFriendlyMessage)
          logState("Error", userFriendlyMessage)
          Timber.e(e, "Error loading sources: ${e.message}")
        }
      }
    }

    private fun logState(
      state: String,
      data: Any?,
    ) {
      when (data) {
        is List<*> -> Timber.d("SourcesUiState: $state - Sources count: ${data.size}")
        is String -> Timber.d("SourcesUiState: $state - Message: $data")
        null -> Timber.d("SourcesUiState: $state")
      }
    }
  }
