package com.yllielshani.twocentsdemo.presentation.postlist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.presentation.navigation.NavRoutes
import com.yllielshani.twocentsdemo.presentation.UiState

@Composable
fun HomeScreen(
    uiState: UiState<List<PostDto>>,
    onRetry: () -> Unit,
    onPostClick: (String) -> Unit,
    onPosterNetWorthClick: (String) -> Unit
) {
    when (uiState) {
        UiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }

        UiState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("No items available", style = MaterialTheme.typography.bodyLarge)
        }

        is UiState.Error -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Error",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onRetry) { Text("Retry") }
                }
            }
        }

        is UiState.Success -> {
            val list = uiState.data
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp
                )
            ) {
                itemsIndexed(list) { index, item ->
                    PostCard(
                        number = index + 1,
                        item = item,
                        onClick = { onPostClick(item.uuid) },
                        onPosterNetWorthClick = { onPosterNetWorthClick(item.authorUuid) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeRoute(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { viewModel.loadItems() }
    val state = viewModel.uiState.collectAsState().value

    HomeScreen(
        uiState = state,
        onRetry = viewModel::loadItems,
        onPostClick = { id ->
            navController.navigate(NavRoutes.PostDetails.createRoute(id))
        },
        onPosterNetWorthClick = { id ->
            Log.d("nav-debug", "TAP balance → navigate to author_posts/$id")
            navController.navigate(NavRoutes.AuthorPosts.createRoute(id))
        }
    )
}