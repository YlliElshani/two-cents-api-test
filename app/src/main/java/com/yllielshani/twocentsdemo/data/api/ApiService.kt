package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.api.params.GetItemsParams
import com.yllielshani.twocentsdemo.data.api.params.GetPostDetailsParams
import com.yllielshani.twocentsdemo.data.model.PostDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    suspend fun getItems(): List<PostDto>
    suspend fun getPostById(id: String): PostDto

  //@POST("v1/posts/arena")
  //suspend fun getItems(
  //    @Body request: JsonRpcRequest<GetItemsParams>
  //): JsonRpcResponse<List<PostDto>>

  //@POST("jsonrpc")
  //suspend fun getUserInfo(
  //    @Body request: JsonRpcRequest<GetPostDetailsParams>
  //): JsonRpcResponse<PostDto>
}