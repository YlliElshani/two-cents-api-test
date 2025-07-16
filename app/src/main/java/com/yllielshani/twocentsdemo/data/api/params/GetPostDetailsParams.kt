package com.yllielshani.twocentsdemo.data.api.params

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPostDetailsParams(val postId: String)