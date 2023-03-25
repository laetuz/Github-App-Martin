package com.neotica.submissiondicodingawal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase =
            instance ?:synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java, "Github.db"
                ).build()
            }
    }
}