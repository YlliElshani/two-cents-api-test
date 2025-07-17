package com.yllielshani.twocentsdemo.presentation.detail

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yllielshani.twocentsdemo.data.model.PostDto
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
    uiState: UiState<PostDto>,
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
            Log.d("yll1","got error: ${uiState.message}")
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Error",
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
                Text(text = item.text, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(12.dp))
                Text("Posted by: ${item.authorMetaDto.age}y ${item.authorMetaDto.gender} from ${item.authorMetaDto.arena}")
            }
        }
    }
}