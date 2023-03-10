package com.neotica.submissiondicodingawal.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object GithubViewModelFactory:ViewModelProvider.Factory {
    private val githubRepoManager = GithubRepoManager()
    private val githubRepo = GithubRepo(githubRepoManager)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GithubViewModel(githubRepo) as T
    }
}