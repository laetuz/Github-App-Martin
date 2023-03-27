/*
package com.neotica.submissiondicodingawal.main.di

import android.content.Context
import com.neotica.submissiondicodingawal.main.utils.AppExecutors
import com.neotica.submissiondicodingawal.mvvm.GithubRepo
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import com.neotica.submissiondicodingawal.room.GithubDatabase

//Step 7: Create object for Injection
object Injection {
    //Step 7.1: Create a function that returns to NewsRepository
    // with context as a parameter
    fun provideRepository(context: Context): GithubViewModel {
        //Step 7.2: Create variable that contains Service, database, dao, and appExecutors
        val apiService = ApiConfig.getApiService()
        //Step 7.3: Provide context to database
        val database = GithubDatabase.getInstance(context)
        val dao = database.dao()
        val appExecutors = AppExecutors()
        //Step 7.4: Returns instances from apiService, dao, and appExecutors
        return GithubViewModel.getInstance(apiService, dao, appExecutors)
    }
}*/
