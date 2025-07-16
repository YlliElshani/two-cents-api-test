package com.yllielshani.twocentsdemo.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.yllielshani.twocentsdemo.data.model.ItemDto
import com.yllielshani.twocentsdemo.presentation.UiState

@Composable
fun PostDetailsRoute(
    postId: String,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPost(postId)
    }

    PostDetailsScreen(uiState = uiState, onRetry = { viewModel.loadPost(postId) })
}

@Composable
fun PostDetailsScreen(
    uiState: UiState<ItemDto>,
    onRetry: () -> Unit
) {
    when (uiState) {
        UiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }

        UiState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("No post data", style = MaterialTheme.typography.bodyLarge)
        }

        is UiState.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Error: ${uiState.message}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRetry) { Text("Retry") }
            }
        }

        is UiState.Success -> {
            val item = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text(text = item.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(12.dp))
                Text("Posted by: ${item.posterInfo.age}y ${item.posterInfo.gender} from ${item.posterInfo.location}")
            }
        }
    }
}