package com.neotica.submissiondicodingawal.core.domain.home

import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

interface IHomeRepo {
    val userResponse: MutableStateFlow<List<GithubResponseItem>?>
    val isLoading: MutableStateFlow<Boolean>

    suspend fun getUser()
}