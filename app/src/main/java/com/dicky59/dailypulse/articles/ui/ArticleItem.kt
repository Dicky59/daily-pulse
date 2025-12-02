package com.dicky59.dailypulse.articles.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicky59.dailypulse.articles.presentation.Article

@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
  Card(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
      if (article.imageUrl.isNotEmpty()) {
        AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
        )
      }

      Text(
              text = article.title,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
      )

      if (article.description.isNotEmpty()) {
        Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
        )
      }

      Text(
              text = article.date,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.fillMaxWidth(),
              textAlign = androidx.compose.ui.text.style.TextAlign.End
      )
    }
  }
}
