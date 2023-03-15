package com.neotica.submissiondicodingawal.mvvm

import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.retrofit.ApiClient
import com.neotica.submissiondicodingawal.retrofit.ApiConfig

class GithubRepoManager {
    private val apiConfig = ApiConfig.getApiService()

    fun getUser(): GithubResponseItem = apiConfig.getUsername()
}