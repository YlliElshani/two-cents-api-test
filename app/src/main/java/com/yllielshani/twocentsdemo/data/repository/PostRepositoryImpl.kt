package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.api.ApiService
import com.yllielshani.twocentsdemo.data.api.JsonRpcRequest
import com.yllielshani.twocentsdemo.data.enums.Filter
import com.yllielshani.twocentsdemo.data.model.PostDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun fetchItems(filter: Filter, secretKey: String?): Result<List<PostDto>> =
        withContext(Dispatchers.IO) {
            val params = buildMap<String, Any> {
                put("filter", filter.value)
                secretKey?.let { put("secret_key", it) }
            }
            val request = JsonRpcRequest(id = "anon", method = "/v1/posts/arena", params = params)
            runCatching {
                val response = apiService.getItems(request)
                response.result?.items ?: throw Throwable(response.error?.message.orEmpty())
            }
        }

    override suspend fun fetchItemById(id: String): PostDto {
        return apiService.getPostById(id)
    }
}