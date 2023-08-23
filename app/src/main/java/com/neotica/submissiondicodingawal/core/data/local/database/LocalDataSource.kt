package com.neotica.submissiondicodingawal.core.data.local.database

import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val dao: Dao){
    fun getUser(): Flow<List<Entity>> = dao.getGithub()
}