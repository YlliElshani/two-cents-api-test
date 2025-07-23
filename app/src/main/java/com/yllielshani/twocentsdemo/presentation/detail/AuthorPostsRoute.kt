package com.yllielshani.twocentsdemo.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yllielshani.twocentsdemo.presentation.NavRoutes
import com.yllielshani.twocentsdemo.presentation.posts.HomeScreen
import com.yllielshani.twocentsdemo.presentation.posts.HomeViewModel

@Composable
fun AuthorPostsRoute(
    authorId: String,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(authorId) {
        viewModel.loadPostsByAuthor(authorId)
    }

    val state = viewModel.uiState.collectAsState().value

    HomeScreen(
        uiState = state,
        onRetry = { viewModel.loadPostsByAuthor(authorId) },
        onPostClick = { postId ->
            navController.navigate(NavRoutes.PostDetails.createRoute(postId))
        },
        onPosterNetWorthClick = { nextId ->
            navController.navigate(NavRoutes.AuthorPosts.createRoute(nextId))
        }
    )
}
