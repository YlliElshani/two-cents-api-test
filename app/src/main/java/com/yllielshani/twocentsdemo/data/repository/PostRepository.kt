package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.model.PostDto


interface PostRepository {
    suspend fun fetchItems(): List<PostDto>
    suspend fun fetchItemById(id: String): PostDto
}