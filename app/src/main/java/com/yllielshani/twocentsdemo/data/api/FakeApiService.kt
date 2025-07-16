package com.yllielshani.twocentsdemo.data.api

import com.yllielshani.twocentsdemo.data.enums.Tier
import com.yllielshani.twocentsdemo.data.model.ItemDto
import com.yllielshani.twocentsdemo.data.model.PosterInfo
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeItemApiService @Inject constructor() : ApiService {
    private val items = listOf(
        ItemDto(
            id = "1",
            title = "First Item",
            description = "This is the first item.",
            tier = Tier.Gold,
            posterInfo = PosterInfo(age = 24, gender = "F", location = "Paris", 23423)
        ),
        ItemDto(
            id = "2",
            title = "Second Item",
            description = "This is the second item.",
            tier = Tier.Silver,
            posterInfo = PosterInfo(age = 31, gender = "M", location = "Berlin",120)
        ),
        ItemDto(
            id = "3",
            title = "Third Item",
            description = "This is the third item.",
            tier = Tier.Bronze,
            posterInfo = PosterInfo(age = 28, gender = "F", location = "Tokyo",1000000)
        )
    )

    override suspend fun getItems(): List<ItemDto> {
        delay(2000)
        return items
    }

    override suspend fun getPostById(id: String): ItemDto {
        delay(1000)
        return items.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("Post not found")
    }
}