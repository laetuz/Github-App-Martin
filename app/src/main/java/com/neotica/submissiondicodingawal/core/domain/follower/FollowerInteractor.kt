package com.neotica.submissiondicodingawal.core.domain.follower

import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

class FollowerInteractor(private val iFollowerRepo: IFollowerRepo) {
    val followerResponse: MutableStateFlow<List<GithubResponseItem>?> = iFollowerRepo.followerResponse
    val isLoading: MutableStateFlow<Boolean> = iFollowerRepo.isLoading

    suspend fun getFollower(name: String){
        iFollowerRepo.getFollower(name)
    }
}