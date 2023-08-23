package com.neotica.submissiondicodingawal.core.domain.database

import com.neotica.submissiondicodingawal.core.data.local.database.Entity
import kotlinx.coroutines.flow.Flow

interface DaoInterface {
    fun getGithub(): Flow<List<Entity>>
    fun insertBookmark(github: Entity)
    fun updateBookmark(github: Entity)
    fun deleteAll()
    fun deleteUser(username: String)
    fun isUserBookmarked(username: String): Boolean
}