package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostMetaDto (
    @Json(name = "poll")
    val poll: List<String>?,
    @Json(name = "post_type")
    val postType: Int?
)