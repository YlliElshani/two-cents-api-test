package com.yllielshani.twocentsdemo.data.api

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class JsonRpcRequest(
    val jsonrpc: String = "2.0",
    val id: String = "anon",
    val method: String,
    val params: Map<String, Any>
)

data class JsonRpcResponse<T>(
    val jsonrpc: String?,
    val id: String?,
    val result: T?,
    val error: JsonRpcError?
)

data class JsonRpcError(
    val code: Int,
    val message: String
)