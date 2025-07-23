package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.AuthorPostsDto
import com.yllielshani.twocentsdemo.data.model.PollResultsWrapper
import com.yllielshani.twocentsdemo.data.model.PostListWrapperDto
import com.yllielshani.twocentsdemo.data.model.PostWrapperDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("prod")
    suspend fun getPostById(
        @Body request: JsonRpcRequest
    ): JsonRpcResponse<PostWrapperDto>

    @POST("prod")
    suspend fun getAllPosts(
        @Body request: JsonRpcRequest
    ): JsonRpcResponse<PostListWrapperDto>

    @POST("prod")
    suspend fun getPostsPerAuthor(
        @Body request: JsonRpcRequest
    ): JsonRpcResponse<AuthorPostsDto>

    @POST("prod")
    suspend fun getPollResults(@Body request: JsonRpcRequest): JsonRpcResponse<PollResultsWrapper>
}