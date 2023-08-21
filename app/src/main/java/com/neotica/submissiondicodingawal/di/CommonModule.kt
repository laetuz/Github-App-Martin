package com.neotica.submissiondicodingawal.di

import com.neotica.submissiondicodingawal.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.data.local.repo.FollowingRepo
import com.neotica.submissiondicodingawal.data.local.repo.HomeRepo
import com.neotica.submissiondicodingawal.data.local.repo.SearchRepo
import com.neotica.submissiondicodingawal.data.local.repo.UserDetailRepo
import com.neotica.submissiondicodingawal.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.domain.following.FollowingInteractor
import com.neotica.submissiondicodingawal.domain.following.IFollowingRepo
import com.neotica.submissiondicodingawal.domain.home.HomeInteractor
import com.neotica.submissiondicodingawal.domain.home.IHomeRepo
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { GithubViewModel(get(), get(), get(), get(), get(), get(), get()) }
}

val repositoryModule = module {
    //single { FollowingRepo(get()) }
    single { FollowerRepo(get()) }
    single { UserDetailRepo(get()) }
    single { SearchRepo(get()) }
}

val useCase = module {
    single { HomeInteractor(get()) }
    single { provideHomeRepo(get()) }
    single { FollowingInteractor(get()) }
    single { provideFollowingRepo(get()) }
}

private fun provideHomeRepo(apiService: ApiService): IHomeRepo {
    return HomeRepo(apiService)
}

private fun provideFollowingRepo(apiService: ApiService): IFollowingRepo{
    return FollowingRepo(apiService)
}