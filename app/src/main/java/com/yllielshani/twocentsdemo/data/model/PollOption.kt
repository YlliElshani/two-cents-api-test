package com.yllielshani.twocentsdemo.data.model

import java.math.BigDecimal

data class PollOption(
    val question: String,
    val votes: Int,
    val averageBalance: BigDecimal
)