package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostListWrapperDto(
    @Json(name = "posts")
    val posts: List<PostDto>
)