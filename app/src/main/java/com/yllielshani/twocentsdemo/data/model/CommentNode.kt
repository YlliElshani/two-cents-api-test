package com.yllielshani.twocentsdemo.data.model

data class CommentNode(
    val comment: CommentsDto,
    val children: MutableList<CommentNode> = mutableListOf()
)