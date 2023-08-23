package com.neotica.submissiondicodingawal.core.domain.following

import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

interface IFollowingRepo {
    val followingResponse: MutableStateFlow<List<GithubResponseItem>?>
    val isLoading: MutableStateFlow<Boolean>

    suspend fun getFollowing(name: String)
}