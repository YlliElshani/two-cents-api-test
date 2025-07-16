package com.yllielshani.twocentsdemo.presentation.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.data.model.ItemDto
import androidx.hilt.navigation.compose.hiltViewModel
import com.yllielshani.twocentsdemo.data.enums.Tier
import com.yllielshani.twocentsdemo.data.model.PosterInfo

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit
) {
    when (uiState) {
        HomeUiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }

        HomeUiState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("No items available", style = MaterialTheme.typography.bodyLarge)
        }

        is HomeUiState.Error -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Error: ${(uiState as HomeUiState.Error).message}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onRetry) { Text("Retry") }
                }
            }
        }

        is HomeUiState.Success -> {
            val list = (uiState as HomeUiState.Success).items
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp
                )
            ) {
                itemsIndexed(list) { index, item ->
                    ItemCard(
                        number = list.size - index,
                        item = item,
                        onClick = {}
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(uiState = state, onRetry = viewModel::loadItems)
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Loading() {
    HomeScreen(uiState = HomeUiState.Loading, onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Empty() {
    HomeScreen(uiState = HomeUiState.Empty, onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Error() {
    HomeScreen(uiState = HomeUiState.Error("Network failed"), onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun Preview_HomeScreen_Success() {
    HomeScreen(
        uiState = HomeUiState.Success(
            listOf(
                ItemDto(
                    1, "A", "Alpha", Tier.Silver,
                    posterInfo = PosterInfo(age = 24, gender = "F", location = "Paris", 23423)
                ),
                ItemDto(
                    2, "B", "Beta", Tier.Gold,
                    posterInfo = PosterInfo(age = 24, gender = "F", location = "Paris", 23423)
                )
            )
        ),
        onRetry = {}
    )
}
