package com.neotica.submissiondicodingawal.view.di

import com.neotica.submissiondicodingawal.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.data.local.repo.FollowingRepo
import com.neotica.submissiondicodingawal.data.local.repo.HomeRepo
import com.neotica.submissiondicodingawal.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val viewModule = module {
    viewModel { GithubViewModel(get(), get(), get(), get(), get(), get()) }
}

val diskIO: Executor = Executors.newSingleThreadExecutor()

val repositoryModule = module {
    single { HomeRepo(get()) }
    single { FollowingRepo(get()) }
    single { FollowerRepo(get()) }
}
