package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.AuthorPostsDto
import com.yllielshani.twocentsdemo.data.model.PostDto
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
  suspend fun getItems(
      @Body request: JsonRpcRequest
  ): JsonRpcResponse<PostListWrapperDto>

    @POST("prod")
    suspend fun getItemsPerAuthor(
        @Body request: JsonRpcRequest
    ): JsonRpcResponse<AuthorPostsDto>

  @POST("jsonrpc")
  suspend fun getUserInfo(
      @Body request: JsonRpcRequest
  ): JsonRpcResponse<PostDto>
}