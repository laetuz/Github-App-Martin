package com.neotica.submissiondicodingawal.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity("github")
class Entity (
    @field:ColumnInfo("username")
    @field:PrimaryKey
    val username: String,
    @field:ColumnInfo("imageUrl")
    val imageUrl: String? = null,
    @field:ColumnInfo("bookmarked")
    val isBookmarked: Boolean
)