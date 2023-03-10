package com.neotica.submissiondicodingawal.mvvm

import com.neotica.submissiondicodingawal.response.GithubResponseItem

class GithubRepo(private val githubRepoManager: GithubRepoManager) {
    suspend fun getUser(): Result<GithubResponseItem, Exception> {
        return try {
            val response = githubRepoManager.getUser()
            Result.build { response }
        } catch (e: Exception) {
            Result.build { throw e }
        }
    }
}