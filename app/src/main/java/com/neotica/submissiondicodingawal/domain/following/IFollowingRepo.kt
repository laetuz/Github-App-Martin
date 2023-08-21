package com.neotica.submissiondicodingawal.domain.following

import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

interface IFollowingRepo {
    val followingResponse: MutableStateFlow<List<GithubResponseItem>?>
    val isLoading: MutableStateFlow<Boolean>

    suspend fun getFollowing(name: String)
}