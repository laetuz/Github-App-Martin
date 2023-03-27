package com.neotica.submissiondicodingawal.main.di

import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module{
    viewModel { GithubViewModel(get()) }
}

val repositoryModule = module{

}
