package com.yllielshani.twocentsdemo.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yllielshani.twocentsdemo.presentation.detail.AuthorPostsRoute
import com.yllielshani.twocentsdemo.presentation.detail.PostDetailsRoute
import com.yllielshani.twocentsdemo.presentation.items.HomeRoute

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
            arguments = listOf(navArgument("postId") {
                type = NavType.StringType
            })
        ) {
            val postId = it.arguments?.getString("postId") ?: return@composable
            PostDetailsRoute(postId)
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