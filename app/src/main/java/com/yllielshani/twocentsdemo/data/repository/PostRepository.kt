package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.enums.Filter
import com.yllielshani.twocentsdemo.data.model.CommentsResponseWrapper
import com.yllielshani.twocentsdemo.data.model.PollResultsWrapper
import com.yllielshani.twocentsdemo.data.model.PostDto


interface PostRepository {
    suspend fun fetchPosts(filter: Filter, secretKey: String? = null): Result<List<PostDto>>
    suspend fun fetchPostsPerAuthor(filter: Filter, secretKey: String? = null, authorId: String): Result<List<PostDto>>
    suspend fun fetchPostById(id: String, secretKey: String?): Result<PostDto>
    suspend fun fetchPollResults(id: String, secretKey: String?): Result<PollResultsWrapper>
    suspend fun fetchCommentsPerPost(id: String, secretKey: String?): Result<CommentsResponseWrapper>
}