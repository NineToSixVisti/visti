package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.Image

@Composable
fun ImageItem(image: Image) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box {
            NetworkImage(
                url = image.downloadUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .size(200.dp)
            )
            Text(
                text = image.author.uppercase(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}