package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.ItemDto

interface ApiService {
    suspend fun getItems(): List<ItemDto>
}