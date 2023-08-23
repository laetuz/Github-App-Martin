package com.neotica.submissiondicodingawal.core.data.local.database

import com.neotica.submissiondicodingawal.core.domain.database.DaoInterface
import kotlinx.coroutines.flow.Flow

class DaoRepo(private val dao: Dao): DaoInterface {
    override fun getGithub(): Flow<List<Entity>> {
        return dao.getGithub()
    }

    override fun insertBookmark(github: Entity) {
        return dao.insertBookmark(github)
    }

    override fun updateBookmark(github: Entity) {
        dao.updateBookmark(github)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun deleteUser(username: String) {
        dao.deleteUser(username)
    }

    override fun isUserBookmarked(username: String): Boolean {
        return dao.isUserBookmarked(username)
    }
}