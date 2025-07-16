package com.yllielshani.twocentsdemo.data.model

import com.yllielshani.twocentsdemo.data.enums.Tier

data class ItemDto (
    val id: String,
    val title: String,
    val description: String,
    val tier: Tier,
    val posterInfo: PosterInfo
)