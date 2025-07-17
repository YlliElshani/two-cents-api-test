package com.yllielshani.twocentsdemo.data.api.params

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GetFeedPostsParams(val filters: String)