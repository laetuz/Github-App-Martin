package com.neotica.submissiondicodingawal.mvvm

import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.retrofit.ApiClient

class GithubRepoManager {
    private val apiClient = ApiClient.instance

    suspend fun getUser(): GithubResponseItem = apiClient.getUsername()
}