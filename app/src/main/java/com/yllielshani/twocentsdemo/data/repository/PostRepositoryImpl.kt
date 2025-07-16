package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.api.ApiService
import com.yllielshani.twocentsdemo.data.api.JsonRpcRequest
import com.yllielshani.twocentsdemo.data.api.RpcException
import com.yllielshani.twocentsdemo.data.api.params.GetItemsParams
import com.yllielshani.twocentsdemo.data.model.PostDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun fetchItems(): List<PostDto> {
       //val request = JsonRpcRequest(
       //    method = "/v1/posts/arena",
       //    params = GetItemsParams(page = 1, pageSize = 20)
       //)

       //val response = apiService.getItems(request)

       //return when {
       //    response.error != null -> {
       //        throw RpcException(
       //            code = response.error.code,
       //            message = response.error.message
       //        )
       //    }

       //    response.result != null -> response.result

       //    else -> throw RpcException(
       //        code = -1,
       //        message = "Unknown RPC error: no result or error"
       //    )
       //}
        return apiService.getItems()
    }
    override suspend fun fetchItemById(id: String): PostDto {
        return apiService.getPostById(id)
    }
}