package com.yllielshani.twocentsdemo.presentation.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.data.model.PostDto
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yllielshani.twocentsdemo.data.enums.Tier
import com.yllielshani.twocentsdemo.data.model.PosterInfo
import com.yllielshani.twocentsdemo.presentation.NavRoutes
import com.yllielshani.twocentsdemo.presentation.UiState

@Composable
fun HomeScreen(
    uiState: UiState<List<PostDto>>,
    onRetry: () -> Unit,
    onPostClick: (String) -> Unit
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
                        "Error: ${(uiState as UiState.Error).message}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onRetry) { Text("Retry") }
                }
            }
        }

        is UiState.Success -> {
            val list = (uiState as UiState.Success).data
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
                        onClick = { onPostClick(item.id) }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HomeRoute(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(uiState = state, onRetry = viewModel::loadItems, onPostClick = { postId ->
        navController.navigate(NavRoutes.PostDetails.createRoute(postId))
    })
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Loading() {
    HomeScreen(uiState = UiState.Loading, onRetry = {}, onPostClick = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Empty() {
    HomeScreen(uiState = UiState.Empty, onRetry = {}, onPostClick = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Error() {
    HomeScreen(uiState = UiState.Error("Network failed"), onRetry = {}, onPostClick = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Success() {
    HomeScreen(
        uiState = UiState.Success(
            listOf(
                PostDto(
                    "1", "A", "Alpha", Tier.Silver,
                    posterInfo = PosterInfo(age = 24, gender = "F", location = "Paris", 23423)
                ),
                PostDto(
                    "2", "B", "Beta", Tier.Gold,
                    posterInfo = PosterInfo(age = 24, gender = "F", location = "Paris", 23423)
                )
            )
        ),
        onRetry = {},
        onPostClick = {}
    )
}
