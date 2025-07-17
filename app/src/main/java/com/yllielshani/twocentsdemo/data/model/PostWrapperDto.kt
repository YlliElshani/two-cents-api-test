package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostWrapperDto(
    @Json(name = "posts")
    val items: List<PostDto>
)