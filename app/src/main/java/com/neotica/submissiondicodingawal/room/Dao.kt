package com.neotica.submissiondicodingawal.room

import androidx.lifecycle.LiveData
import androidx.room.*

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM github ORDER BY username desc")
    fun getGithub(): LiveData<List<Entity>>

    @Query("SELECT * FROM github where bookmarked = 1")
    fun getBookmarked(): LiveData<List<Entity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(github: List<Entity>)

    @Update
    fun updateBookmark(github: Entity)

    @Query("DELETE FROM github WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM github WHERE username = :username AND bookmarked = 1)")
    fun isUserBookmarked(username: String): Boolean
}