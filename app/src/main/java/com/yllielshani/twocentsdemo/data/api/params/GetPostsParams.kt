package com.yllielshani.twocentsdemo.data.api.params

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GetItemsParams(
    val page: Int,
    val pageSize: Int
)