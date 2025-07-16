package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.model.ItemDto


interface ItemRepository {
    suspend fun fetchItems(): List<ItemDto>
}