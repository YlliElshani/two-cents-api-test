package com.yllielshani.twocentsdemo.data.api

import com.squareup.moshi.JsonClass
import java.util.UUID


@JsonClass(generateAdapter = true)
data class JsonRpcRequest<Params>(
    val jsonrpc: String = "2.0",
    val id: String = UUID.randomUUID().toString(),
    val method: String,
    val params: Params
)

@JsonClass(generateAdapter = true)
data class JsonRpcResponse<Result>(
    val jsonrpc: String,
    val id: String,
    val result: Result?,
    val error: RpcError?
)

@JsonClass(generateAdapter = true)
data class RpcError(
    val code: Int,
    val message: String,
    val data: Any? = null
)