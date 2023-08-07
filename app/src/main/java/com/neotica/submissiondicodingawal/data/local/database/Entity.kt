package com.neotica.submissiondicodingawal.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("github")
class Entity(
    @field:ColumnInfo("username")
    @field:PrimaryKey
    val username: String,
    @field:ColumnInfo("imageUrl")
    val imageUrl: String? = null,
    @field:ColumnInfo("bookmarked")
    var isBookmarked: Boolean
)