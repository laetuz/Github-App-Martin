package com.neotica.submissiondicodingawal.domain

import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import kotlinx.coroutines.flow.StateFlow

class HomeInteractor(private val homeRepo: IHomeRepo) {
    val userResponse: StateFlow<List<GithubResponseItem>?> = homeRepo.userResponse
    val isLoading: StateFlow<Boolean> = homeRepo.isLoading

    suspend fun fetchUser(){
        homeRepo.getUser()
    }
}