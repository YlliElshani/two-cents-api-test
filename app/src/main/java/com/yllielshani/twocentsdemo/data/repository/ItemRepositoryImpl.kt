package com.yllielshani.twocentsdemo.data.repository

import com.yllielshani.twocentsdemo.data.api.ApiService
import com.yllielshani.twocentsdemo.data.model.ItemDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {
    override suspend fun fetchItems(): List<ItemDto> {
        return apiService.getItems()
    }
    override suspend fun fetchItemById(id: String): ItemDto {
        return apiService.getPostById(id)
    }
}