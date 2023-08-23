package com.neotica.submissiondicodingawal.core.di

import com.neotica.submissiondicodingawal.core.data.local.database.DaoRepo
import com.neotica.submissiondicodingawal.core.data.local.database.LocalDataSource
import com.neotica.submissiondicodingawal.core.data.local.repo.DatabaseRepo
import com.neotica.submissiondicodingawal.core.data.local.repo.UserDetailRepo
import com.neotica.submissiondicodingawal.core.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.core.domain.database.DaoInteractor
import com.neotica.submissiondicodingawal.core.domain.database.DaoInterface
import com.neotica.submissiondicodingawal.core.domain.follower.IFollowerRepo
import com.neotica.submissiondicodingawal.core.domain.following.FollowingInteractor
import com.neotica.submissiondicodingawal.core.domain.following.IFollowingRepo
import com.neotica.submissiondicodingawal.core.domain.home.HomeInteractor
import com.neotica.submissiondicodingawal.core.domain.home.IHomeRepo
import com.neotica.submissiondicodingawal.core.data.local.database.Dao
import com.neotica.submissiondicodingawal.core.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.core.domain.follower.FollowerInteractor
import com.neotica.submissiondicodingawal.core.data.local.repo.FollowingRepo
import com.neotica.submissiondicodingawal.data.local.repo.HomeRepo
import com.neotica.submissiondicodingawal.core.data.local.repo.SearchRepo
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { GithubViewModel(get(), get(), get(), get(), get(), get(), get()) }
}

val repositoryModule = module {
   // single { FollowerRepo(get()) }
    single { UserDetailRepo(get()) }
    single { SearchRepo(get()) }
    single { LocalDataSource(get()) }
    single { DatabaseRepo(get(),get()) }
    single { DaoRepo(get()) }
}

val useCase = module {
    single { HomeInteractor(get()) }
    single { provideHomeRepo(get()) }
    single { FollowingInteractor(get()) }
    single { provideFollowingRepo(get()) }
    single { FollowerInteractor(get()) }
    single { provideFollowerRepo(get()) }

    single { DaoInteractor(get()) }
    single { provideDaoRepo(get()) }
}

private fun provideDaoRepo(dao: Dao): DaoInterface {
    return DaoRepo(dao)
}

private fun provideHomeRepo(apiService: ApiService): IHomeRepo {
    return HomeRepo(apiService)
}

private fun provideFollowingRepo(apiService: ApiService): IFollowingRepo {
    return FollowingRepo(apiService)
}

private fun provideFollowerRepo(apiService: ApiService): IFollowerRepo{
    return FollowerRepo(apiService)
}