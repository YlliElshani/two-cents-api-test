package com.yllielshani.twocentsdemo.data.model

import com.yllielshani.twocentsdemo.data.enums.Tier

data class PostDto (
    val id: String,
    val title: String,
    val description: String,
    val tier: Tier,
    val posterInfo: PosterInfo
)