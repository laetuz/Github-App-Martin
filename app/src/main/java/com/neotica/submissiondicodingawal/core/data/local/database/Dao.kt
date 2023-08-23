package com.neotica.submissiondicodingawal.core.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM github ORDER BY username desc")
    fun getGithub(): Flow<List<Entity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(github: Entity)

    @Update
    fun updateBookmark(github: Entity)

    @Query("DELETE FROM github WHERE bookmarked = 0")
    fun deleteAll()

    @Query("DELETE FROM github WHERE username = :username")
    fun deleteUser(username: String)

    @Query("SELECT EXISTS(SELECT * FROM github WHERE username = :username AND bookmarked = 1)")
    fun isUserBookmarked(username: String): Boolean
}