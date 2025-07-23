package com.yllielshani.twocentsdemo.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yllielshani.twocentsdemo.presentation.detail.AuthorPostsRoute
import com.yllielshani.twocentsdemo.presentation.detail.PostDetailsRoute
import com.yllielshani.twocentsdemo.presentation.posts.HomeRoute

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Items.route
    ) {
        composable(route = NavRoutes.Items.route) {
            HomeRoute(navController)
        }
        composable(
            route = NavRoutes.PostDetails.route,
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: return@composable
            PostDetailsRoute(
                postId = postId,
                onPosterNetWorthClick = { authorId ->
                    navController.navigate(NavRoutes.AuthorPosts.createRoute(authorId))
                }
            )
        }
        composable(
            NavRoutes.AuthorPosts.route,
            arguments = listOf(navArgument("authorId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("authorId") ?: return@composable
            AuthorPostsRoute(id, navController)
        }
    }
}