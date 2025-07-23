package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class PollResultsWrapper(
    @Json(name = "results")
    val results: Map<String, PollResultsDto>
)

@JsonClass(generateAdapter = true)
data class PollResultsDto(
    @Json(name = "votes")
    val votes: Int,

    @Json(name = "average_balance")
    val averageBalance: BigDecimal
)