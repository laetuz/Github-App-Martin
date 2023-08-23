package com.neotica.submissiondicodingawal.core.domain.database

import com.neotica.submissiondicodingawal.core.data.local.database.DaoRepo
import com.neotica.submissiondicodingawal.core.data.local.database.Entity
import kotlinx.coroutines.flow.Flow

class DaoInteractor(private val daoRepo: DaoRepo) {
    fun getUser(): Flow<List<Entity>>{
        return daoRepo.getGithub()
    }
    fun addUser(entity: Entity) {
        daoRepo.insertBookmark(entity)
    }
    fun deleteUser(username: String){
        daoRepo.deleteUser(username)
    }
}