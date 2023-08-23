package com.neotica.submissiondicodingawal.core.domain.following

import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.MutableStateFlow

class FollowingInteractor(private val iFollowingRepo: IFollowingRepo) {
    val followingResponse: MutableStateFlow<List<GithubResponseItem>?> = iFollowingRepo.followingResponse
    val isLoading: MutableStateFlow<Boolean> = iFollowingRepo.isLoading

    suspend fun getFollowing(name: String){
        iFollowingRepo.getFollowing(name)
    }
}