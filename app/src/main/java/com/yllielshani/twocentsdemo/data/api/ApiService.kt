package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.AuthorPostsDto
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.data.model.PostWrapperDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    suspend fun getPostById(id: String): PostDto

  @POST("prod")
  @Headers(
      "Content-Type: application/json",
      "Accept: application/json"
  )
  suspend fun getItems(
      @Body request: JsonRpcRequest
  ): JsonRpcResponse<PostWrapperDto>

    @POST("prod")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getItemsPerAuthor(
        @Body req: JsonRpcRequest
    ): JsonRpcResponse<AuthorPostsDto>

  @POST("jsonrpc")
  suspend fun getUserInfo(
      @Body request: JsonRpcRequest
  ): JsonRpcResponse<PostDto>
}