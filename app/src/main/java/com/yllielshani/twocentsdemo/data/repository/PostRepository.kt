package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.enums.Filter
import com.yllielshani.twocentsdemo.data.model.PostDto


interface PostRepository {
    suspend fun fetchItems(filter: Filter, secretKey: String? = null): Result<List<PostDto>>
    suspend fun fetchItemById(id: String): PostDto
}