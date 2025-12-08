package com.dicky59.dailypulse.sources.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dicky59.dailypulse.sources.presentation.SourceViewModel
import com.dicky59.dailypulse.sources.presentation.SourcesUiState

@Composable
fun SourceScreen(viewModel: SourceViewModel = hiltViewModel()) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()

  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    Text(
            text = "Sources",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
    )

    when (val state = uiState.value) {
      is SourcesUiState.Loading -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
      }
      is SourcesUiState.Error -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Text(
                  text = state.message,
                  style = MaterialTheme.typography.bodyLarge,
                  color = MaterialTheme.colorScheme.error,
          )
        }
      }
      is SourcesUiState.Success -> {
        LazyColumn { items(state.sources) { source -> SourceItem(source = source) } }
      }
    }
  }
}
