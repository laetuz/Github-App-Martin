package com.neotica.submissiondicodingawal.di

import android.content.Context
import com.neotica.submissiondicodingawal.mvvm.GithubRepo
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import com.neotica.submissiondicodingawal.room.GithubDatabase
import com.neotica.submissiondicodingawal.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): GithubRepo {
        val apiService = ApiConfig.getApiService()
        val database = GithubDatabase.getInstance(context)
        val dao = database.dao()
        val appExecutors = AppExecutors()
        return GithubRepo.getInstance(apiService, dao, appExecutors)
    }
}