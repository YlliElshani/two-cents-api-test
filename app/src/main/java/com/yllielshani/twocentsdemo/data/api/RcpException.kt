package com.yllielshani.twocentsdemo.data.api

class RpcException(val code: Int, override val message: String) : Exception(message)