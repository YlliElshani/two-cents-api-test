package com.yllielshani.twocentsdemo.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

class BigDecimalAdapter {

    @FromJson
    fun fromJson(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @ToJson
    fun toJson(value: BigDecimal?): String? {
        return value?.toPlainString()
    }
}