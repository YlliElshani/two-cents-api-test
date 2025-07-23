package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.api.ApiService
import com.yllielshani.twocentsdemo.data.api.JsonRpcRequest
import com.yllielshani.twocentsdemo.data.enums.Filter
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.data.model.PostWrapperDto
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
                response.result?.posts ?: throw Throwable(response.error?.message.orEmpty())
            }
        }

    override suspend fun fetchPostsPerAuthor(
        filter: Filter,
        secretKey: String?,
        authorId: String
    ): Result<List<PostDto>> = withContext(Dispatchers.IO) {
        val params = mapOf("user_uuid" to authorId)
        val request = JsonRpcRequest(
            id = "anon",
            method = "/v1/users/get",
            params = params
        )

        runCatching {
            val wrapper = apiService.getItemsPerAuthor(request).result
                ?: throw Throwable("Empty RPC result")

            wrapper.recentPosts
        }
    }

    override suspend fun fetchPostById(id: String, secretKey: String?): Result<PostDto> =
        withContext(Dispatchers.IO) {
            val params = buildMap<String, Any> {
                put("post_uuid", id)
                secretKey?.let { put("secret_key", it) }
            }
            val request = JsonRpcRequest(id = "anon", method = "/v1/posts/get", params = params)
            runCatching {
                val response = apiService.getPostById(request)
                val post = response.result?.postDetails
                    ?: throw Throwable("Post not found or response was malformed.")
                post
            }
        }

}