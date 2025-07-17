package com.yllielshani.twocentsdemo.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant

class InstantAdapter {

    @FromJson
    fun fromJson(value: String?): Instant? {
        return value?.let { Instant.parse(it) }
    }

    @ToJson
    fun toJson(value: Instant?): String? {
        return value?.toString()
    }
}