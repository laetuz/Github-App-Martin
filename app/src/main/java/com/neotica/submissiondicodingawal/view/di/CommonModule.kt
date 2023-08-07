package com.neotica.submissiondicodingawal.view.di

import com.neotica.submissiondicodingawal.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.data.local.repo.FollowingRepo
import com.neotica.submissiondicodingawal.data.local.repo.HomeRepo
import com.neotica.submissiondicodingawal.data.local.repo.SearchRepo
import com.neotica.submissiondicodingawal.data.local.repo.UserDetailRepo
import com.neotica.submissiondicodingawal.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { GithubViewModel(get(), get(), get(), get(), get(), get(), get()) }
}

val repositoryModule = module {
    single { HomeRepo(get()) }
    single { FollowingRepo(get()) }
    single { FollowerRepo(get()) }
    single { UserDetailRepo(get()) }
    single { SearchRepo(get()) }
}
