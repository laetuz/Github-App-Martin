package com.neotica.submissiondicodingawal.core.domain.follower

import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

interface IFollowerRepo {
    val followerResponse: MutableStateFlow<List<GithubResponseItem>?>
    val isLoading: MutableStateFlow<Boolean>

    suspend fun getFollower(name: String)
}