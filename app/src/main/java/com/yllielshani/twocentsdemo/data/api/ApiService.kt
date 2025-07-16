package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.PostDto

interface ApiService {
    suspend fun getItems(): List<PostDto>
    suspend fun getPostById(id: String): PostDto
}