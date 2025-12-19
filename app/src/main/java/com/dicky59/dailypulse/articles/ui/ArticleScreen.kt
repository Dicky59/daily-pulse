package com.dicky59.dailypulse.articles.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dicky59.dailypulse.articles.presentation.ArticleUiState
import com.dicky59.dailypulse.articles.presentation.ArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(viewModel: ArticleViewModel = hiltViewModel()) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var showBottomSheet by remember { mutableStateOf(false) }

  LaunchedEffect(uiState.value) {
    val state = uiState.value
    if (state is ArticleUiState.Success && state.aiSummary != null) {
      showBottomSheet = true
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
      Text(
        text = "Articles",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp),
      )

      when (val state = uiState.value) {
        is ArticleUiState.Loading -> {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
          }
        }
        is ArticleUiState.Error -> {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
              text = state.message,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.error,
            )
          }
        }
        is ArticleUiState.Success -> {
          LazyColumn { items(state.articles) { article -> ArticleItem(article = article) } }

          if (showBottomSheet && state.aiSummary != null) {
            ModalBottomSheet(
              onDismissRequest = {
                showBottomSheet = false
                viewModel.dismissAiSummary()
              },
              sheetState = sheetState,
            ) {
              AiSummaryBottomSheet(
                summary = state.aiSummary.summary,
                sentiment = state.aiSummary.sentiment,
              )
            }
          }
        }
      }
    }

    FloatingActionButton(
      onClick = { viewModel.getAiSummary() },
      modifier = Modifier.align(Alignment.BottomEnd).padding(end = 24.dp, bottom = 110.dp),
    ) {
      Icon(
        imageVector = Icons.Default.AutoAwesome,
        contentDescription = "AI Summary",
      )
    }
  }
}

@Composable
private fun AiSummaryBottomSheet(
  summary: String,
  sentiment: String,
) {
  Column(
    modifier = Modifier.fillMaxWidth().padding(24.dp),
  ) {
    Text(
      text = "AI Summary",
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 16.dp),
    )

    Text(
      text = summary,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.padding(bottom = 16.dp),
    )

    Text(
      text = "Sentiment: $sentiment",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.primary,
    )
  }
}
