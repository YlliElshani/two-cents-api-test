package com.yllielshani.twocentsdemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class CommentsResponseWrapper(
    @Json(name = "comments")
    val comments: List<CommentsDto>
)

@JsonClass(generateAdapter = true)
data class CommentsDto(
    @Json(name = "uuid")
    val uuid: String,

    @Json(name = "created_at")
    val createdAt: Instant,

    @Json(name = "updated_at")
    val updatedAt: Instant,

    @Json(name = "post_uuid")
    val postUuid: String,

    @Json(name = "reply_parent_uuid")
    val replyParentUuid: String,

    @Json(name = "author_uuid")
    val authorUuid: String,

    @Json(name = "author_meta")
    val authorMeta: AuthorMetaDto,

    @Json(name = "text")
    val text: String,

    @Json(name = "upvote_count")
    val upvoteCount: Int,

    @Json(name = "report_count")
    val reportCount: Int,

    @Json(name = "deleted_at")
    val deletedAt: String?
)