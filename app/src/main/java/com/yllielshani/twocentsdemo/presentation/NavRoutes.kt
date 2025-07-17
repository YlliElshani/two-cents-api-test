package com.yllielshani.twocentsdemo.presentation


sealed class NavRoutes(val route: String) {
    data object Items : NavRoutes("items")
    data object PostDetails : NavRoutes("post_details/{postId}") {
        fun createRoute(postId: String) = "post_details/$postId"
    }

    data object AuthorPosts : NavRoutes("author_posts/{authorId}") {
        fun createRoute(authorId: String) = "author_posts/$authorId"
    }
}