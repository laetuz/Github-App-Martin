package com.neotica.submissiondicodingawal.main.di

import androidx.room.Room
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.room.GithubDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val viewModule = module {
    viewModel { GithubViewModel(get(), get(), get()) }
}

val daoModule = module {
    single {
        Room.databaseBuilder(androidApplication(), GithubDatabase::class.java, "github").build()
    }
    single { get<GithubDatabase>().dao() }
}

val diskIO: Executor = Executors.newSingleThreadExecutor()

val repositoryModule = module {

}
