package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorPostsDto(
    @Json(name = "user")
    val userInfo: AuthorMetaDto,
    @Json(name = "recentPosts")
    val recentPosts: List<PostDto>
)