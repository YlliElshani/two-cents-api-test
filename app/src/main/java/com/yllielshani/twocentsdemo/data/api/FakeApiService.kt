package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.model.ItemDto
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeItemApiService @Inject constructor() : ApiService {
    override suspend fun getItems(): List<ItemDto> {
        delay(2000)
        return listOf(
            ItemDto(1, "First Item", "This is the first item."),
            ItemDto(2, "Second Item", "This is the second item."),
            ItemDto(3, "Third Item", "This is the third item.")
        )
    }
}