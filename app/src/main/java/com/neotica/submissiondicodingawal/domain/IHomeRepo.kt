package com.neotica.submissiondicodingawal.domain

import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

interface IHomeRepo {
    val userResponse: MutableStateFlow<List<GithubResponseItem>?>
    val isLoading: MutableStateFlow<Boolean>

    suspend fun getUser()
}